package com.moretech.vtb.service

import com.moretech.vtb.client.WalletClient
import com.moretech.vtb.client.dto.NftTransferDto
import com.moretech.vtb.client.dto.TransferDto
import com.moretech.vtb.controller.auction.dto.AuctionMessageType
import com.moretech.vtb.controller.auction.dto.BidDto
import com.moretech.vtb.controller.auction.dto.CreateBidDto
import com.moretech.vtb.controller.auction.dto.CreateLotDto
import com.moretech.vtb.controller.auction.dto.LotDto
import com.moretech.vtb.controller.auction.dto.NotifyAuctioneersMessage
import com.moretech.vtb.entity.User
import com.moretech.vtb.entity.auction.Bid
import com.moretech.vtb.entity.auction.Lot
import com.moretech.vtb.entity.auction.LotStatus
import com.moretech.vtb.repository.BidRepo
import com.moretech.vtb.repository.LotRepo
import com.moretech.vtb.repository.NFTRepo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class AuctionService(
        private val lotRepo: LotRepo,
        private val nftRepo: NFTRepo,
        private val bidRepo: BidRepo,
        private val simpMessagingTemplate: SimpMessagingTemplate,
        private val walletClient: WalletClient
) {

    fun getOpenLots() = lotRepo.findAllByStatus(LotStatus.IN_PROGRESS).map {
        LotDto(
                id = it.id!!,
                creatorUsername = it.creator.username,
                nftImageId = it.nft.image.id!!,
                initialPrice = it.initialPrice,
                step = it.step,
                createdAt = it.createdAt,
                status = it.status,
                title = it.title,
                description = it.description
        )
    }

    fun createLot(user: User, createLotDto: CreateLotDto) {
        nftRepo.findById(createLotDto.nftId).ifPresent {
            lotRepo.save(
                    Lot(
                            creator = user,
                            nft = it,
                            initialPrice = createLotDto.initialPrice,
                            step = createLotDto.step,
                            description = createLotDto.description,
                            title = createLotDto.title
                    )
            )
        }
    }

    fun createBid(user: User, createBidDto: CreateBidDto, lotId: Long) {
        lotRepo.findById(lotId).ifPresent { lot ->
            bidRepo.save(
                    Bid(
                            owner = user,
                            lot = lot,
                            value = createBidDto.bid
                    )
            )
        }
        simpMessagingTemplate.convertAndSend(
                "/topic/$lotId/auc",
                NotifyAuctioneersMessage(
                        type = AuctionMessageType.BID,
                        currentBid = createBidDto.bid,
                        currentUserName = user.username,
                        history = bidRepo.findAllByLotId(lotId).map {
                            BidDto(
                                    it.createdAt,
                                    it.value,
                                    it.owner.username
                            )
                        }.sortedByDescending { it.createdAt }
                )
        )
    }

    fun finishLot(lotId: Long) {
        lotRepo.findById(lotId).ifPresent { lot ->
            val winnerBid = bidRepo.findFirstByLotIdOrderByCreatedAtDesc(lotId)

            simpMessagingTemplate.convertAndSend(
                    "/topic/$lotId/auc",
                    NotifyAuctioneersMessage(
                            type = AuctionMessageType.SOLD,
                            currentBid = winnerBid.value,
                            history = bidRepo.findAllByLotId(lotId).map {
                                BidDto(
                                        it.createdAt,
                                        it.value,
                                        it.owner.username
                                )
                            }.sortedByDescending { it.createdAt }
                    )
            )
            val lotWallet = lot.creator.nftWallet!!
            val winnerWallet = winnerBid.owner.nftWallet!!
            walletClient.transferRuble(
                    TransferDto(
                            winnerWallet.privateKey,
                            lotWallet.publicKey,
                            winnerBid.value
                    )
            ).block()
            walletClient.transferNft(
                    NftTransferDto(
                            lotWallet.privateKey,
                            winnerWallet.publicKey,
                            walletClient.getNftBalance(lotWallet.publicKey).block()!!
                                    .balance.find { it.uri == lot.nft.id.toString() }!!.tokens[0]
                    )
            ).block()
            lot.status = LotStatus.SOLD
            lotRepo.save(lot)
        }
    }

    fun getLot(lotId: Long) =
            lotRepo.findById(lotId).get().let {
                LotDto(
                        id = it.id!!,
                        creatorUsername = it.creator.username,
                        nftImageId = it.nft.image.id!!,
                        initialPrice = it.initialPrice,
                        step = it.step,
                        createdAt = it.createdAt,
                        status = it.status,
                        title = it.title,
                        description = it.description,
                        bids = bidRepo.findAllByLotId(lotId).map { bid ->
                            BidDto(
                                    bid.createdAt,
                                    bid.value,
                                    bid.owner.username
                            )
                        }.sortedByDescending { bid -> bid.createdAt }
                )
            }
}