package com.moretech.vtb.controller.marketplace.dto

import com.moretech.vtb.entity.marketplace.ItemStatus
import java.math.BigDecimal

data class GetItemInfoDto(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val amount: Int,
    val status: ItemStatus,
    val reviews: List<ReviewDto>
)

data class ReviewDto(
    val id: Long,
    val review: String,
    val userId: Long,
    val likes: Int,
    val likedByCurrentUser: Boolean
)
