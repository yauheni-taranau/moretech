package com.moretech.vtb.controller

import com.moretech.vtb.entity.marketplace.ItemReview
import com.moretech.vtb.entity.marketplace.ItemReviewLike
import com.moretech.vtb.repository.ItemReviewLikeRepo
import com.moretech.vtb.service.reward.PeriodicRewardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val itemReviewLikeRepo: ItemReviewLikeRepo,
    private val periodicRewardService: PeriodicRewardService
) {

//    @GetMapping
//    fun test(): MutableList<Long>? {
//        return periodicRewardService.rewardMostLikedReviews()
//    }
}