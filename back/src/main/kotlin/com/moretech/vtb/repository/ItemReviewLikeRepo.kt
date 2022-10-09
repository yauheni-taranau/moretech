package com.moretech.vtb.repository

import com.moretech.vtb.entity.marketplace.ItemReview
import com.moretech.vtb.entity.marketplace.ItemReviewLike
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ItemReviewLikeRepo : CrudRepository<ItemReviewLike, Long> {

    fun findByItemReview(itemReview: ItemReview): List<ItemReviewLike>

    @Query(
        """
            select user_id as c
            from moretech.public.item_review_like as r
            where date_part('day', now() - r.created_at) <= 30
            group by r.user_id
            order by c desc
            LIMIT 3
    """, nativeQuery = true
    )
    fun getTopUsersByLikedCommentsDuringMonth(): List<Long>
}