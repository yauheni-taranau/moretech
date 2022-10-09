package com.moretech.vtb.games

data class GameEventMessage(
        var gameEvent: GameEvent,
        var cellId: Int? = null,
        var usernameWinner: String? = null,
        var joinedPlayerUsername: String? = null,
        var currentTurnUsername: String? = null,
        var currentTurnType: TurnType? = null
)

enum class GameEvent {
    PLAYER_JOINED, DRAW, WIN, TURN
}