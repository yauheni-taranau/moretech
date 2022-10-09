package com.moretech.vtb.entity

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class TransferRubblesQueue(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @OneToOne
        @JoinColumn(name = "transfer_to_wallet")
        var transferTo: NFTWallet,

        @OneToOne
        @JoinColumn(name = "transfer_from_wallet")
        var transferFrom: NFTWallet,

        var transferAmount: BigDecimal
)