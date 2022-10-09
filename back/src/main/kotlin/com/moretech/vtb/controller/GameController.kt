package com.moretech.vtb.controller

import com.moretech.vtb.controller.AuthUtils.AUTH_HEADER
import com.moretech.vtb.controller.dto.game.GameCreatedDto
import com.moretech.vtb.controller.dto.game.GameDto
import com.moretech.vtb.controller.dto.game.GameTurnDto
import com.moretech.vtb.controller.dto.ttt.CreateTTTDto
import com.moretech.vtb.service.TTTService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ttt")
class GameController(
    private val tttService: TTTService
) {

    @PostMapping
    fun createGame(@RequestBody ttt: CreateTTTDto, @RequestHeader(value = AUTH_HEADER) token: String): GameCreatedDto {
        return tttService.createGame(ttt, token)
    }

    @PostMapping("/{id}/join")
    fun joinGame(@PathVariable id: Long, @RequestHeader(value = AUTH_HEADER) token: String) {
        tttService.joinGame(id, token)
    }

    @PostMapping("/{id}/turn")
    fun turn(
            @PathVariable id: Long,
            @RequestHeader(value = AUTH_HEADER) token: String,
            @RequestBody gameTurnDto: GameTurnDto
    ) {
        tttService.makeTurn(id, gameTurnDto, token)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): GameDto {
        return tttService.getById(id)
    }
}