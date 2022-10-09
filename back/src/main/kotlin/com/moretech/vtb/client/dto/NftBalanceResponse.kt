package com.moretech.vtb.client.dto

data class NftBalanceResponse(
    val balance: List<Balance>
)

data class Balance(
    val uri: String,
    val tokens: List<Long>
)
