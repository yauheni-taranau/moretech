package com.moretech.vtb.service

import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.controller.marketplace.dto.CreatePositionDto
import com.moretech.vtb.controller.marketplace.dto.GetItemInfoDto
import com.moretech.vtb.controller.marketplace.dto.ReviewDto
import com.moretech.vtb.controller.marketplace.dto.UserReviewDto
import com.moretech.vtb.entity.Image
import com.moretech.vtb.entity.User
import com.moretech.vtb.entity.marketplace.Item
import com.moretech.vtb.entity.marketplace.ItemReview
import com.moretech.vtb.entity.marketplace.ItemReviewLike
import com.moretech.vtb.entity.marketplace.ItemStatus
import com.moretech.vtb.repository.ImageRepo
import com.moretech.vtb.repository.ItemRepo
import com.moretech.vtb.repository.ItemReviewLikeRepo
import com.moretech.vtb.repository.ItemReviewRepo
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class MarketPlaceService(
    private val itemRepo: ItemRepo,
    private val imageRepo: ImageRepo,
    private val walletService: WalletService,
    private val itemReviewRepo: ItemReviewRepo,
    private val itemReviewLikeRepo: ItemReviewLikeRepo
) {
    fun getAllOpenItems() = itemRepo.findAllByStatus(ItemStatus.OPEN)

    fun createPosition(user: User, createPositionDto: CreatePositionDto, image: MultipartFile): Item {
        val savedImage = imageRepo.save(
            Image(
                content = image.bytes,
                name = image.originalFilename!!
            )
        )
        return itemRepo.save(
            Item(
                name = createPositionDto.name,
                description = createPositionDto.description,
                price = createPositionDto.price,
                status = ItemStatus.OPEN,
                user = user,
                image = savedImage,
                amount = createPositionDto.amount
            )
        )
    }

    @Transactional
    fun buyItem(user: User, itemId: Long) {
        val item = itemRepo.findById(itemId).get()
        if (item.status == ItemStatus.SOLD) {
            throw IllegalArgumentException("SOLD OUT")
        }
        walletService.transferRubles(
            user.nftWallet!!,
            TransferRequest(
                item.user!!.username,
                item.price
            )
        )
        item.amount--
        if (item.amount == 0) {
            item.status = ItemStatus.SOLD
        }
        itemRepo.save(item)
    }

    fun getAllItems(): MutableIterable<Item> {
        return itemRepo.findAll()
    }

    fun getItemById(user: User, id: Long): Any {
        val item = itemRepo.findById(id).get()
        val reviews = itemReviewRepo.findAllByItem(item)

        return GetItemInfoDto(
            id = item.id!!,
            name = item.name,
            description = item.description,
            price = item.price,
            amount = item.amount,
            status = item.status,
            reviews = reviews.map {
                val likes = it.likes
                ReviewDto(
                    id = it.id!!,
                    review = it.review,
                    userId = it.user!!.id!!,
                    likes = likes.count(),
                    likedByCurrentUser = likes.stream().filter { like -> like.user!!.id == user.id }.findAny().isPresent
                )
            }
        )
    }

    fun addReview(user: User, userReviewDto: UserReviewDto, itemId: Long) {
        val item = itemRepo.findById(itemId).get()
        itemReviewRepo.save(
            ItemReview(
                review = userReviewDto.review,
                user = user,
                item = item,
                createdAt = LocalDateTime.now()
            )
        )
    }

    fun likeReview(user: User, reviewId: Long) {
        val review = itemReviewRepo.findById(reviewId).get()
        if (!review.likes.stream().filter { user.id == it.user!!.id }.findFirst().isPresent) {
            itemReviewLikeRepo.save(
                ItemReviewLike(
                    user = user,
                    itemReview = review,
                    createdAt = LocalDateTime.now()
                )
            )
        }
    }
}