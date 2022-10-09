package com.moretech.vtb.repository

import com.moretech.vtb.entity.auction.NFT
import org.springframework.data.repository.CrudRepository

interface NFTRepo : CrudRepository<NFT, Long> {

}