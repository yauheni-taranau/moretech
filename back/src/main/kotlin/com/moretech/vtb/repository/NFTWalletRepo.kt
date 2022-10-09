package com.moretech.vtb.repository

import com.moretech.vtb.entity.NFTWallet
import org.springframework.data.repository.CrudRepository

interface NFTWalletRepo: CrudRepository<NFTWallet, Long> {
}