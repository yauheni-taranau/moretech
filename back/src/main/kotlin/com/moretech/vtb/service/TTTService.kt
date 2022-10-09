package com.moretech.vtb.service

import com.moretech.vtb.controller.dto.game.GameCreatedDto
import com.moretech.vtb.controller.dto.game.GameDto
import com.moretech.vtb.controller.dto.game.GameTurnDto
import com.moretech.vtb.controller.dto.ttt.CreateTTTDto
import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.entity.User
import com.moretech.vtb.games.*
import com.moretech.vtb.repository.GameRepo
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap

@Service
class TTTService(
        private val userService: UserService,
        private val gameRepo: GameRepo,
        private val simpMessagingTemplate: SimpMessagingTemplate,
        private val walletService: WalletService
) {

    private val gameBoards: MutableMap<Long, GameData> = ConcurrentHashMap()

    fun createGame(ttt: CreateTTTDto, token: String): GameCreatedDto {
        val game = gameRepo.save(
                Game(
                        bid = ttt.bid,
                        firstPlayer = userService.decryptUser(token),
                        name = ttt.name
                )
        )
        gameBoards[game.id!!] = GameData(
                currentTurnUsername = game.firstPlayer.username,
                currentType = TurnType.KRESTIK,
                first = game.firstPlayer.username
        )
        return GameCreatedDto(game.id!!, game.name, game.firstPlayer.username)
    }

    fun joinGame(id: Long, token: String): Boolean {
        val game = gameRepo.findById(id).get()
        game.secondPlayer = userService.decryptUser(token)
        val gameData = gameBoards[id]!!
        if (gameData.second != null) {
            throw IllegalArgumentException("Net mest")
        }
        gameData.second = game.secondPlayer!!.username
        gameRepo.save(game)
        simpMessagingTemplate.convertAndSend(
                "/topic/" + game.id + "/events",
                GameEventMessage(
                        gameEvent = GameEvent.PLAYER_JOINED,
                        joinedPlayerUsername = game.secondPlayer!!.username,
                        currentTurnUsername = game.firstPlayer.username,
                        currentTurnType = TurnType.KRESTIK
                )
        )
        return true
    }

    fun makeTurn(id: Long, gameTurnDto: GameTurnDto, token: String) {
        val gameData = gameBoards[id]!!
        val currentUser = userService.decryptUser(token)
        if (gameData.currentTurnUsername != currentUser.username) {
            throw IllegalArgumentException("Nelzya hodit")
        }
        if (gameData.board[gameTurnDto.cellId] == "-") {
            gameData.board[gameTurnDto.cellId] = gameData.currentType.value
        } else {
            throw IllegalArgumentException("Zanyato")
        }
        val result = checkLine(gameData)
        invertTurnType(gameData)
        nextUserTurn(gameData, currentUser)
        if (result != null) {
            val usernameWinner = if (result == TurnType.KRESTIK) gameData.first else gameData.second
            simpMessagingTemplate.convertAndSend(
                    "/topic/$id/events",
                    GameEventMessage(
                            gameEvent = GameEvent.WIN,
                            usernameWinner = usernameWinner,
                            cellId = gameTurnDto.cellId,
                            currentTurnUsername = gameData.currentTurnUsername,
                            currentTurnType = gameData.currentType,
                    )
            )
            transferMoney(usernameWinner, gameData.first, gameData.second, id)
            gameBoards.remove(id)
        } else {
            if (checkDraw(gameData)) {
                simpMessagingTemplate.convertAndSend(
                        "/topic/$id/events",
                        GameEventMessage(
                                gameEvent = GameEvent.DRAW,
                                cellId = gameTurnDto.cellId,
                                currentTurnUsername = gameData.currentTurnUsername,
                                currentTurnType = gameData.currentType,
                        )
                )
                gameBoards.remove(id)
            } else {
                simpMessagingTemplate.convertAndSend(
                        "/topic/$id/events",
                        GameEventMessage(
                                gameEvent = GameEvent.TURN,
                                currentTurnUsername = gameData.currentTurnUsername,
                                currentTurnType = gameData.currentType,
                                cellId = gameTurnDto.cellId
                        )
                )
            }
        }
    }

    private fun transferMoney(usernameWinner: String?, first: String, second: String?, gameId: Long) {
        val game = gameRepo.findById(gameId).get()
        val (winnerUsername, loserUsername) = if (first == usernameWinner!!) first to second else second to first
        val winner = userService.getUser(winnerUsername!!)
        val loser = userService.getUser(loserUsername!!)

        walletService.transferRubles(
            loser.nftWallet!!,
            TransferRequest(
                winner.nftWallet!!.publicKey,
                game.bid
            )
        )
    }

    private fun checkDraw(gameData: GameData): Boolean {
        return !gameData.board.any { it == "-" }
    }

    private fun checkLine(gameData: GameData): TurnType? {
        val board = gameData.board
        var turn: TurnType? = null
        for (a in 0..7) {
            var line: String? = null
            when (a) {
                0 -> line = board[0] + board[1] + board[2]
                1 -> line = board[3] + board[4] + board[5]
                2 -> line = board[6] + board[7] + board[8]
                3 -> line = board[0] + board[3] + board[6]
                4 -> line = board[1] + board[4] + board[7]
                5 -> line = board[2] + board[5] + board[8]
                6 -> line = board[0] + board[4] + board[8]
                7 -> line = board[2] + board[4] + board[6]
            }
            when (line) {
                "XXX" -> {
                    turn = TurnType.KRESTIK
                }

                "OOO" -> {
                    turn = TurnType.NOLIK
                }
            }
        }
        return turn
    }

    private fun nextUserTurn(gameData: GameData, user: User) {
        if (gameData.first == user.username) {
            gameData.currentTurnUsername = gameData.second!!
        } else {
            gameData.currentTurnUsername = gameData.first
        }
    }

    private fun invertTurnType(gameData: GameData) {
        val type = gameData.currentType
        if (type == TurnType.KRESTIK) {
            gameData.currentType = TurnType.NOLIK
        } else {
            gameData.currentType = TurnType.KRESTIK
        }
    }

    fun getById(id: Long): GameDto {
        val game = gameRepo.findById(id).get()
        return GameDto(game.id!!, game.name, game.firstPlayer.username, game.secondPlayer?.username, game.bid)
    }
}