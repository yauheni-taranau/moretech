package com.moretech.vtb.controller.dto.ttt

import java.math.BigDecimal

data class CreateTTTDto(
    val name: String,
    val bid: BigDecimal = BigDecimal.TEN
)
