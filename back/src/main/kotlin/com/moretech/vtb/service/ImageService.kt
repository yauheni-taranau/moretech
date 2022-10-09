package com.moretech.vtb.service

import com.moretech.vtb.repository.ImageRepo
import org.springframework.stereotype.Service

@Service
class ImageService(
        private val imageRepo: ImageRepo
) {

    fun download(id: Long) = imageRepo.findById(id).get()
}