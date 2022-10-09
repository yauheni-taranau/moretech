package com.moretech.vtb.client.dto

data class NftInfoResponse(
    val tokenId: Long,
    val uri: String,
    val publicKey: String
)