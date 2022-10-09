package com.moretech.vtb.controller.marketplace.dto

import java.math.BigDecimal

data class CreatePositionDto(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val amount: Int
)
