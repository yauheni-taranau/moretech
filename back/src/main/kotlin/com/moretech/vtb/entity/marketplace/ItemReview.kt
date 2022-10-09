package com.moretech.vtb.entity.marketplace

import com.fasterxml.jackson.annotation.JsonIgnore
import com.moretech.vtb.entity.User
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class ItemReview(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var review: String,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    @JsonIgnore
    var user: User? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "itemId")
    var item: Item? = null,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "itemReview")
    @JsonIgnore
    var likes: MutableList<ItemReviewLike> = ArrayList(),

    val createdAt: LocalDateTime
) {
    override fun toString(): String {
        return "asd"
    }
}
