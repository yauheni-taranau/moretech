package com.moretech.vtb.entity.auction

import com.moretech.vtb.entity.User
import java.math.BigDecimal
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Bid(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "ownerId")
    val owner: User,

    val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

    @OneToOne
    @JoinColumn(name = "lotId")
    val lot: Lot,

    val value: BigDecimal
)
