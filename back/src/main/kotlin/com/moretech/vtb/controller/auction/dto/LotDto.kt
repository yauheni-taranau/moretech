package com.moretech.vtb.controller.auction.dto

import com.moretech.vtb.entity.auction.LotStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class LotDto(
    val id: Long,
    val creatorUsername: String,
    val nftImageId: Long,
    val initialPrice: BigDecimal,
    val step: BigDecimal,
    val createdAt: LocalDateTime,
    val status: LotStatus,
    val bids: List<BidDto> = ArrayList(),
    val title: String,
    val description: String
)
