package com.moretech.vtb.service.reward

import com.moretech.vtb.config.property.RewardProperties
import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.repository.ItemReviewRepo
import com.moretech.vtb.repository.UserRepo
import com.moretech.vtb.service.WalletService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class PeriodicRewardService(
    private val rewardProperties: RewardProperties,
    private val itemReviewRepo: ItemReviewRepo,
    private val walletService: WalletService,
    private val userRepo: UserRepo
) {

//    @Scheduled(cron = "*/10 * * * * *")
    fun executeReward() {
//        rewardMostLikedComments()
//        rewardMostTTTWins()
//        rewardMostLikedNews()
        rewardMostLikedReviews()
    }

    fun rewardMostLikedReviews() {
        val reviews = StreamSupport.stream(itemReviewRepo.findAll().spliterator(), false).collect(Collectors.toList())
        val allLikes = reviews.stream().flatMap { it.likes.stream() }
            .filter { LocalDateTime.now().isBefore(it.createdAt.plusDays(30)) }
            .collect(Collectors.toList())

        val reviewToLikes = allLikes.groupBy { it.itemReview!!.id }

        val userToLikes: MutableMap<Long, Int> = HashMap()

        reviewToLikes.entries.forEach { e ->
            val review = reviews.stream().filter { it.id == e.key }.findFirst().get()
            val userId = review.user!!.id!!
            userToLikes[userId] = userToLikes.getOrDefault(userId, 0) + e.value.count()
        }
        val userIds = userToLikes.entries.sortedByDescending { it.value }.stream().limit(3).map { it.key }
            .collect(Collectors.toList())

        userIds.forEach { id ->
            val user = userRepo.findById(id).get()
            walletService.transferRubles(
                user.unit!!.nftWallet!!,
                TransferRequest(
                    user.username, BigDecimal.ONE
                )
            )
        }
    }

    private fun rewardMostLikedNews() {
        TODO("Not yet implemented")
    }

    private fun rewardMostTTTWins() {
        TODO("Not yet implemented")
    }

    private fun rewardMostLikedComments() {
        TODO("Not yet implemented")
    }
}