package com.moretech.vtb.controller

import com.moretech.vtb.repository.AnekdotRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/anekdot")
class AnekdotController(
    private val anekdotRepo: AnekdotRepo
) {

    @GetMapping
    fun getAnekdots() = anekdotRepo.findAllByOrderByLikes()
}