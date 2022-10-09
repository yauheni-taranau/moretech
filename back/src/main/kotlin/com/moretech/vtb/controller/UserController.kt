package com.moretech.vtb.controller

import com.moretech.vtb.controller.dto.LoginDto
import com.moretech.vtb.controller.dto.RegisterUserDto
import com.moretech.vtb.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String) = userService.getUserInfo(username)

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(@RequestBody user: RegisterUserDto) = userService.saveUser(user)

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto) = userService.login(loginDto)

    @GetMapping("/exp/{amount}")
    fun addExp(
        @RequestHeader(value = AuthUtils.AUTH_HEADER) token: String,
        @PathVariable amount: Long
    ) = userService.addExp(userService.decryptUser(token), amount)
}