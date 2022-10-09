package com.moretech.vtb.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NftGenerationResultResponse(
    @JsonProperty("wallet_id")
    val walletId: String,
    val tokens: List<Int>
)