package com.moretech.vtb.games

/**
 * @property matrix 0 - empty, 1 - 1st player pick, 2 - second player pick
 */
data class TTTMessage(
    val gameId: Long,
    var firstPlayerTurn: Boolean = true,
    val matrix: List<List<Int>> = mutableListOf(
        mutableListOf(0, 0, 0),
        mutableListOf(0, 0, 0),
        mutableListOf(0, 0, 0)
    )
)
