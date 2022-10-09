package com.moretech.vtb.controller.dto

import com.moretech.vtb.entity.Role
import java.math.BigDecimal

data class UserInfoDto(
    val id: Long,
    val username: String,
    val name: String,
    val surname: String,
    val balance: BigDecimal,
    val roles: List<Role>
)
