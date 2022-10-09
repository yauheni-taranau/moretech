package com.moretech.vtb.repository

import com.moretech.vtb.entity.Unit
import org.springframework.data.repository.CrudRepository

interface UnitRepo : CrudRepository<Unit, Long> {

    fun findByManagerId(managerId: Long): Unit
}