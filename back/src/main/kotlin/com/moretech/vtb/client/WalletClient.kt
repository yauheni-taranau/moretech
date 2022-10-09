package com.moretech.vtb.client

import com.moretech.vtb.client.dto.BalanceResponse
import com.moretech.vtb.client.dto.NFTGenerateResponse
import com.moretech.vtb.client.dto.NewWalletResponse
import com.moretech.vtb.client.dto.NftBalanceResponse
import com.moretech.vtb.client.dto.NftGenerationRequest
import com.moretech.vtb.client.dto.NftGenerationResultResponse
import com.moretech.vtb.client.dto.NftInfoResponse
import com.moretech.vtb.client.dto.NftTransferDto
import com.moretech.vtb.client.dto.TransactionHistoryRequest
import com.moretech.vtb.client.dto.TransactionHistoryResponse
import com.moretech.vtb.client.dto.TransactionResponse
import com.moretech.vtb.client.dto.TransactionStatus
import com.moretech.vtb.client.dto.TransferDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@ReactiveFeignClient(name = "nft", url = "\${nft.url}")
interface WalletClient {

    @PostMapping("$WALLETS/new")
    fun createWallet(): Mono<NewWalletResponse>

    @PostMapping("$TRANSFER/matic")
    fun transferMatic(@RequestBody transferDto: TransferDto): Mono<TransactionResponse>

    @PostMapping("$TRANSFER/ruble")
    fun transferRuble(@RequestBody transferDto: TransferDto): Mono<TransactionResponse>

    @PostMapping("$TRANSFER/ruble")
    fun transferRubleSTR(@RequestBody transferDto: TransferDto): Mono<String>

    @PostMapping("$TRANSFER$NFT")
    fun transferNft(@RequestBody transferDto: NftTransferDto): Mono<TransactionResponse>

    @GetMapping("/transfers/status/{transactionHash}")
    fun getTransactionStatus(@PathVariable transactionHash: String): Mono<TransactionStatus>

    @GetMapping("$WALLETS/{publicKey}/balance")
    fun getWalletBalance(@PathVariable publicKey: String): Mono<BalanceResponse>

    @GetMapping("$WALLETS/{publicKey}$NFT/balance")
    fun getNftBalance(@PathVariable publicKey: String): Mono<NftBalanceResponse>

    @PostMapping("$NFT/generate")
    fun generateNft(@RequestBody request: NftGenerationRequest): Mono<NFTGenerateResponse>

    @GetMapping("$NFT/{tokenId}")
    fun getNftInfo(@PathVariable tokenId: String): Mono<NftInfoResponse>

    @GetMapping("$NFT/generate/{transactionHash}")
    fun getGeneratedNftResult(@PathVariable transactionHash: String): Mono<NftGenerationResultResponse>

    @PostMapping("$WALLETS/{publicKey}/history")
    fun getTransactionHistory(@PathVariable publicKey: String,
                              @RequestBody transactionHistoryRequest: TransactionHistoryRequest): Mono<TransactionHistoryResponse>

    companion object {
        private const val WALLETS = "/wallets"
        private const val TRANSFER = "/transfers"
        private const val NFT = "/nft"
    }
}