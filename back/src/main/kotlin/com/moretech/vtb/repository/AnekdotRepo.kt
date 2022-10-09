package com.moretech.vtb.repository

import com.moretech.vtb.entity.Anekdot
import org.springframework.data.repository.CrudRepository

interface AnekdotRepo: CrudRepository<Anekdot, Long> {

    fun findAllByOrderByLikes(): List<Anekdot>
}