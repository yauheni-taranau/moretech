package com.moretech.vtb.repository

import com.moretech.vtb.entity.User
import com.moretech.vtb.entity.marketplace.Item
import com.moretech.vtb.entity.marketplace.ItemStatus
import org.springframework.data.repository.CrudRepository

interface ItemRepo : CrudRepository<Item, Long> {

    fun findAllByUser(user: User): List<Item>

    fun findAllByStatus(status: ItemStatus): List<Item>
}