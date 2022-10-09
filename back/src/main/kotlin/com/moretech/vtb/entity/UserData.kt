package com.moretech.vtb.entity

data class UserData(

        val name: String,
        val surname: String,
        val username: String,
        val roles: List<Role>
)