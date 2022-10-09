package com.moretech.vtb.controller.marketplace

import com.moretech.vtb.controller.AuthUtils
import com.moretech.vtb.controller.marketplace.dto.CreatePositionDto
import com.moretech.vtb.controller.marketplace.dto.UserReviewDto
import com.moretech.vtb.service.MarketPlaceService
import com.moretech.vtb.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/market")
class MarketplaceController(
    private val marketPlaceService: MarketPlaceService,
    private val userService: UserService
) {

    @GetMapping("/items")
    fun getAllItems() = marketPlaceService.getAllItems()

    @GetMapping("/items/open")
    fun getAllAvailableItems() = marketPlaceService.getAllOpenItems()

    @GetMapping("/items/{id}")
    fun getItemById(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @PathVariable id: Long
    ) = marketPlaceService.getItemById(decrypt(token), id)

    @PostMapping("/items", consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPosition(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @RequestPart("data") createPositionDto: CreatePositionDto,
        @RequestPart(name = "image") image: MultipartFile
    ) = marketPlaceService.createPosition(decrypt(token), createPositionDto, image)

    @PostMapping("/items/{itemId}/buy")
    fun buyItem(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @PathVariable itemId: Long
    ) = marketPlaceService.buyItem(decrypt(token), itemId)

    @PostMapping("/items/{id}/review")
    fun addReview(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @RequestBody userReviewDto: UserReviewDto,
        @PathVariable id: Long
    ) = marketPlaceService.addReview(decrypt(token), userReviewDto, id)

    @GetMapping("/items/reviews/{reviewId}/like")
    fun likeReview(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @PathVariable reviewId: Long
    ) = marketPlaceService.likeReview(decrypt(token), reviewId)

    private fun decrypt(token: String) = userService.decryptUser(token)
}