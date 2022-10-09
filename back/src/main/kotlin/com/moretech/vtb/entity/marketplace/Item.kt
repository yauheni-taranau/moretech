package com.moretech.vtb.entity.marketplace

import com.fasterxml.jackson.annotation.JsonIgnore
import com.moretech.vtb.entity.Image
import com.moretech.vtb.entity.User
import java.math.BigDecimal
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    val name: String,
    val description: String,
    val price: BigDecimal,
    var status: ItemStatus,
    var amount: Int,

    @JsonIgnore
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    var user: User? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "imageId")
    var image: Image? = null
)
