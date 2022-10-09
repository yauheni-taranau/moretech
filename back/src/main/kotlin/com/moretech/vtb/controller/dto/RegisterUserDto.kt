package com.moretech.vtb.controller.dto

data class RegisterUserDto(
    val username: String,
    val password: String,
    val name: String,
    val surname: String
)
