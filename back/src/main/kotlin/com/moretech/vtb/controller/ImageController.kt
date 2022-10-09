package com.moretech.vtb.controller

import com.moretech.vtb.service.ImageService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/images")
class ImageController(
        private val imageService: ImageService
) {

    @GetMapping("/{id}")
    fun downloadImage(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val image = imageService.download(id)
        val header = HttpHeaders()
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + image.name)
        header.contentLength = image.content.size.toLong()
        return ResponseEntity
                .ok()
                .headers(header)
                .body(image.content)
    }
}