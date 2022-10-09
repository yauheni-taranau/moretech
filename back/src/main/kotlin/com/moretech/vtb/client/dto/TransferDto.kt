package com.moretech.vtb.client.dto

import java.math.BigDecimal

data class TransferDto(
    val fromPrivateKey: String,
    val toPublicKey: String,
    val amount: BigDecimal
)