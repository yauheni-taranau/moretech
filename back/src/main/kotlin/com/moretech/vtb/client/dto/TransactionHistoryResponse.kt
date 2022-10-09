package com.moretech.vtb.client.dto

data class TransactionHistoryResponse(
    val history: String
)

data class History(
    val blockNumber: Int,
    val timeStamp: Long,
    val contractAddress: String,
    val from: String,
    val to: String,
    val gasUsed: String,
    val confirmations: String,
    val tokenName: String,
    val tokenSymbol: String,
    //NFT
    val tokenId: String
)