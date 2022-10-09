package com.moretech.vtb.controller.dto.wallet

import java.math.BigDecimal

data class TransferRequest(
    val toUsername: String,
    val amount: BigDecimal
)
