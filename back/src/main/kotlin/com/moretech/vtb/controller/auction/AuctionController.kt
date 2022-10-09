package com.moretech.vtb.controller.auction

import com.moretech.vtb.controller.AuthUtils
import com.moretech.vtb.controller.auction.dto.CreateBidDto
import com.moretech.vtb.controller.auction.dto.CreateLotDto
import com.moretech.vtb.service.AuctionService
import com.moretech.vtb.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auction")
class AuctionController(
    private val auctionService: AuctionService,
    private val userService: UserService
) {

    @GetMapping("/lots")
    fun getAllOpenLots() = auctionService.getOpenLots()

    @GetMapping("/lots/{lotId}")
    fun getLot(
        @PathVariable lotId: Long
    ) = auctionService.getLot(lotId)

    @PostMapping("/lots")
    fun createLot(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @RequestBody createLotDto: CreateLotDto
    ) = auctionService.createLot(userService.decryptUser(token), createLotDto)

    @PostMapping("/lots/{lotId}/bid")
    fun createBid(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @RequestBody createBidDto: CreateBidDto,
        @PathVariable lotId: Long
    ) = auctionService.createBid(userService.decryptUser(token), createBidDto, lotId)

    @PostMapping("/lots/{lotId}/finish")
    fun finishLot(@PathVariable lotId: Long) = auctionService.finishLot(lotId)
}