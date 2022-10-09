package com.moretech.vtb.entity.marketplace

import com.fasterxml.jackson.annotation.JsonIgnore
import com.moretech.vtb.entity.User
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class ItemReviewLike(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    @JsonIgnore
    var user: User? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "itemReviewId")
    @JsonIgnore
    var itemReview: ItemReview? = null,

    val createdAt: LocalDateTime
)
