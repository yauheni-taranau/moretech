package com.moretech.vtb.entity.auction

import com.moretech.vtb.entity.User
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class Lot(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "creatorId")
    val creator: User,

    @OneToOne
    @JoinColumn(name = "nftId")
    val nft: NFT,

    val initialPrice: BigDecimal,
    val step: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var status: LotStatus = LotStatus.IN_PROGRESS,
    val title: String,
    val description: String
)
