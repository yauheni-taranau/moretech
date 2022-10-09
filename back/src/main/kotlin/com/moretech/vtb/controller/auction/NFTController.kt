package com.moretech.vtb.controller.auction

import com.moretech.vtb.controller.AuthUtils
import com.moretech.vtb.controller.auction.dto.CreateNFTDto
import com.moretech.vtb.service.NftService
import com.moretech.vtb.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/nft")
class NFTController(
    private val nftService: NftService,
    private val userService: UserService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun generateNft(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @RequestPart("data") createNFTDto: CreateNFTDto,
        @RequestPart(name = "image") image: MultipartFile
    ) = nftService.createNft(decrypt(token), createNFTDto, image)

    @GetMapping
    fun getUserNfts(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String
    ) = nftService.getUserNfts(decrypt(token))

    private fun decrypt(token: String) = userService.decryptUser(token)
}