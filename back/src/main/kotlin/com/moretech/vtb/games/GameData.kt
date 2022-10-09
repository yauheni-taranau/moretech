package com.moretech.vtb.games

import java.util.concurrent.CopyOnWriteArrayList

data class GameData(
        var board: Array<String> = arrayOf("-", "-", "-", "-", "-", "-", "-", "-", "-"),
        var currentTurnUsername: String,
        var currentType: TurnType,
        val first: String,
        var second: String? = null
)

enum class TurnType(val value: String) {
    KRESTIK("X"), NOLIK("O")
}