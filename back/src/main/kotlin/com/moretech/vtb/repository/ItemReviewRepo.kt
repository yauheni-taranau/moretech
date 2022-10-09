package com.moretech.vtb.repository

import com.moretech.vtb.entity.marketplace.Item
import com.moretech.vtb.entity.marketplace.ItemReview
import org.springframework.data.repository.CrudRepository

interface ItemReviewRepo: CrudRepository<ItemReview, Long> {

    fun findAllByItem(item: Item): List<ItemReview>
}