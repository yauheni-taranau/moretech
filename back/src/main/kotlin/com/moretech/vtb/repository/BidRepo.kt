package com.moretech.vtb.repository

import com.moretech.vtb.entity.auction.Bid
import org.springframework.data.repository.CrudRepository

interface BidRepo : CrudRepository<Bid, Long> {

    fun findAllByLotId(lotId: Long): List<Bid>

    fun findFirstByLotIdOrderByCreatedAtDesc(lotId: Long): Bid
}