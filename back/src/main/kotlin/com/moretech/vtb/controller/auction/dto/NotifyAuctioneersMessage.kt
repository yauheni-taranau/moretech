package com.moretech.vtb.controller.auction.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.sql.Timestamp

data class NotifyAuctioneersMessage(
    val type: AuctionMessageType,
    val currentBid: BigDecimal,
    val currentUserName: String? = null,
    val history: List<BidDto>
)

enum class AuctionMessageType {
    BID, SOLD
}

data class BidDto(
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    val createdAt: Timestamp,
    val value: BigDecimal,
    val username: String
)

