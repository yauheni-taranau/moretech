package com.moretech.vtb.client.dto

data class NftTransferDto(
    val fromPrivateKey: String,
    val toPublicKey: String,
    val tokenId: Long
)
