package com.moretech.vtb.controller.dto.admin

import com.moretech.vtb.controller.dto.UserDto
import java.math.BigDecimal

data class UnitDto(
        var id: Long,
        var name: String,
        var balance: BigDecimal,
        var users: List<UserDto>
)