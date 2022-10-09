package com.moretech.vtb.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NFTGenerateResponse(
    @JsonProperty("transaction_hash")
    val transaction: String
)
