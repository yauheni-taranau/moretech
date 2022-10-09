package com.moretech.vtb.controller.auction.dto

import java.math.BigDecimal

data class CreateLotDto(
    val nftId: Long,
    val initialPrice: BigDecimal,
    val step: BigDecimal,
    val title: String,
    val description: String,
    val amount: Int
)