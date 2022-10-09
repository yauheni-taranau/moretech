package com.moretech.vtb.service

import com.moretech.vtb.client.WalletClient
import com.moretech.vtb.client.dto.NftGenerationRequest
import com.moretech.vtb.controller.auction.dto.CreateNFTDto
import com.moretech.vtb.entity.Image
import com.moretech.vtb.entity.User
import com.moretech.vtb.entity.auction.NFT
import com.moretech.vtb.repository.ImageRepo
import com.moretech.vtb.repository.NFTRepo
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class NftService(
    private val nftClient: WalletClient,
    private val nftRepo: NFTRepo,
    private val imageRepo: ImageRepo,
) {

    fun createWallet() =
        try {
            nftClient.createWallet().block()
        } catch (e: Exception) {
            throw IllegalArgumentException(e)
        }

    fun createNft(user: User, createNFTDto: CreateNFTDto, imageDto: MultipartFile) {
        val image = imageRepo.save(
            Image(
                content = imageDto.bytes,
                name = imageDto.originalFilename!!
            )
        )
        nftRepo.save(NFT(image = image))
        repeat(createNFTDto.amount) {
            nftClient.generateNft(
                NftGenerationRequest(
                    user.nftWallet!!.publicKey,
                    image.id.toString(),
                    createNFTDto.amount
                )
            ).block()
        }
    }

    fun getUserNfts(user: User) =
        nftClient.getNftBalance(
        user.nftWallet!!.publicKey
    ).block()

    companion object : KLogging()
}