package com.moretech.vtb.repository

import com.moretech.vtb.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepo: CrudRepository<User, Long> {

    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean
}