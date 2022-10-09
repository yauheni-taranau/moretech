package com.moretech.vtb.controller.dto.game

import java.math.BigDecimal

class GameDto(
        var id: Long,
        var name: String,
        var first: String,
        var second: String?,
        var bid: BigDecimal
)