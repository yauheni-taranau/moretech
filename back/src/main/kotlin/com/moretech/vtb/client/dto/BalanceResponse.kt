package com.moretech.vtb.client.dto

import java.math.BigDecimal

data class BalanceResponse(
    val maticAmount: BigDecimal,
    val coinsAmount: BigDecimal
)
