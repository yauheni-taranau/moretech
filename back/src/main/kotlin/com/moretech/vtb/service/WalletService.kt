package com.moretech.vtb.service

import com.moretech.vtb.client.WalletClient
import com.moretech.vtb.client.dto.*
import com.moretech.vtb.controller.dto.wallet.GenerateNftRequest
import com.moretech.vtb.controller.dto.wallet.TransferNftRequest
import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.entity.Image
import com.moretech.vtb.entity.NFTWallet
import com.moretech.vtb.entity.User
import com.moretech.vtb.repository.ImageRepo
import com.moretech.vtb.repository.UserRepo
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class WalletService(
    private val walletClient: WalletClient,
    private val userRepo: UserRepo,
    private val nftRepo: ImageRepo
) {

    fun transferRubles(fromWallet: NFTWallet, transferRequest: TransferRequest): TransactionResponse? {
        val toUser = userRepo.findByUsername(transferRequest.toUsername)!!
        return walletClient.transferRuble(
                TransferDto(
                        fromWallet.privateKey,
                        toUser.nftWallet!!.publicKey,
                        transferRequest.amount
                )
        ).block()
    }

    fun transferMatic(user: User, transferRequest: TransferRequest): TransactionResponse? {
        val toUser = userRepo.findByUsername(transferRequest.toUsername)!!
        return walletClient.transferMatic(
                TransferDto(
                        user.nftWallet!!.privateKey,
                        toUser.nftWallet!!.publicKey,
                        transferRequest.amount
                )
        ).block()
    }

    fun transferNft(user: User, transferRequest: TransferNftRequest): TransactionResponse? {
        val toUser = userRepo.findByUsername(transferRequest.toUsername)!!
        return walletClient.transferNft(
                NftTransferDto(
                        user.nftWallet!!.privateKey,
                        toUser.nftWallet!!.publicKey,
                        transferRequest.tokenId
                )
        ).block()
    }

    fun getTransactionStatus(transactionHash: String) =
            walletClient.getTransactionStatus(transactionHash).block()

    fun getWalletBalance(user: User) = walletClient.getWalletBalance(user.nftWallet!!.publicKey).block()
    fun getWalletBalance(wallet: NFTWallet) = walletClient.getWalletBalance(wallet.publicKey).block()

    fun getNftBalance(user: User) = walletClient.getNftBalance(user.nftWallet!!.publicKey).block()

    /**
     * uri = imageId
     */
    fun generateNft(user: User, generateNftRequest: GenerateNftRequest, image: MultipartFile): NFTGenerateResponse? {
        val nft = nftRepo.save(
            Image(
                content = image.bytes,
                name = image.originalFilename!!,
                owner = user
            )
        )
        return walletClient.generateNft(
                NftGenerationRequest(
                        user.nftWallet!!.publicKey,
                        nft.id.toString(),
                        generateNftRequest.count
                )
        ).block()
    }

    fun getNftInfo(tokenId: String): NftInfoResponse? {
        return walletClient.getNftInfo(tokenId).block()
    }

    fun getGeneratedNftResult(transactionHash: String): NftGenerationResultResponse? {
        return walletClient.getGeneratedNftResult(transactionHash).block()
    }

    fun getTransactionHistory(user: User, historyRequest: TransactionHistoryRequest): TransactionHistoryResponse? {
        return walletClient.getTransactionHistory(user.nftWallet!!.publicKey, historyRequest).block()
    }
}