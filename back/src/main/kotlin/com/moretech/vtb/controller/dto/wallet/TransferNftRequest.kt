package com.moretech.vtb.controller.dto.wallet

import java.math.BigDecimal

data class TransferNftRequest(
    val toUsername: String,
    val tokenId: Long
)
