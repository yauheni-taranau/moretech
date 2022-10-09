package com.moretech.vtb.client.dto

data class NftGenerationRequest(
    val toPublicKey: String,
    val uri: String,
    val nftCount: Int
)
