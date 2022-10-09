package com.moretech.vtb.entity

import javax.persistence.*

@Entity
data class Unit(

    @Id
    @GeneratedValue
    val id: Long? = null,

    val name: String,

    @ManyToOne
    @JoinColumn(name = "manager_id")
    val manager: User,

    @OneToMany
    val users: MutableList<User> = ArrayList(),

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "nft_wallet_id")
    var nftWallet: NFTWallet? = null
)