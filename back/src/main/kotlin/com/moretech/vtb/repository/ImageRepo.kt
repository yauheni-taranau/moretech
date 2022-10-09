package com.moretech.vtb.repository

import com.moretech.vtb.entity.Image
import org.springframework.data.repository.CrudRepository

interface ImageRepo: CrudRepository<Image, Long> {
}