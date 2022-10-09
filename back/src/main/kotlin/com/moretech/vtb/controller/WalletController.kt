package com.moretech.vtb.controller

import com.moretech.vtb.client.dto.TransactionHistoryRequest
import com.moretech.vtb.controller.AuthUtils.AUTH_HEADER
import com.moretech.vtb.controller.dto.wallet.GenerateNftRequest
import com.moretech.vtb.controller.dto.wallet.TransferNftRequest
import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.service.UserService
import com.moretech.vtb.service.WalletService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/wallet")
class WalletController(
    private val walletService: WalletService,
    private val userService: UserService
) {

    @PostMapping(TRANSFER)
    fun transferRubles(
        @RequestHeader(value = AUTH_HEADER) token: String,
        @RequestBody transferRequest: TransferRequest
    ) = walletService.transferRubles(decryptUser(token).nftWallet!!, transferRequest)


    @PostMapping("$TRANSFER/matic")
    fun transferMatic(
        @RequestHeader(value = AUTH_HEADER) token: String,
        @RequestBody transferRequest: TransferRequest
    ) = walletService.transferMatic(decryptUser(token), transferRequest)

    @PostMapping("$TRANSFER$NFT")
    fun transferNft(
        @RequestHeader(value = AUTH_HEADER) token: String,
        @RequestBody transferRequest: TransferNftRequest
    ) = walletService.transferNft(decryptUser(token), transferRequest)

    @GetMapping("$TRANSFER/{transactionHash}/status")
    fun getTransactionStatus(
        @PathVariable transactionHash: String
    ) = walletService.getTransactionStatus(transactionHash)

    @GetMapping(BALANCE)
    fun getWalletBalance(
        @RequestHeader(value = AUTH_HEADER) token: String
    ) = walletService.getWalletBalance(decryptUser(token))

    @GetMapping("$BALANCE$NFT")
    fun getNftBalance(
        @RequestHeader(value = AUTH_HEADER) token: String
    ) = walletService.getNftBalance(decryptUser(token))

    @PostMapping("/generate-nft")
    fun generateNft(
        @RequestHeader(value = AUTH_HEADER) token: String,
        @RequestBody generateNftRequest: GenerateNftRequest,
        @RequestParam("image") image: MultipartFile
    ) = walletService.generateNft(decryptUser(token), generateNftRequest, image)

    @GetMapping("$NFT/{tokenId}")
    fun getNftInfo(@PathVariable tokenId: String) = walletService.getNftInfo(tokenId)

    @GetMapping("$NFT/generate/{transactionHash}")
    fun getGeneratedNftResult(
        @PathVariable transactionHash: String
    ) = walletService.getGeneratedNftResult(transactionHash)

    @PostMapping("/history")
    fun getTransactionHistory(
        @RequestHeader(value = AUTH_HEADER) token: String,
        @RequestBody historyRequest: TransactionHistoryRequest
    ) = walletService.getTransactionHistory(decryptUser(token), historyRequest)
    
    private fun decryptUser(token: String) = userService.decryptUser(token)

    companion object {
        private const val TRANSFER = "/transfer"
        private const val BALANCE = "/balance"
        private const val NFT = "/nft"
    }
}