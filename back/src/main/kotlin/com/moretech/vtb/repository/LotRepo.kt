package com.moretech.vtb.repository

import com.moretech.vtb.entity.auction.Lot
import com.moretech.vtb.entity.auction.LotStatus
import org.springframework.data.repository.CrudRepository

interface LotRepo : CrudRepository<Lot, Long> {

    fun findAllByStatus(status: LotStatus): List<Lot>
}