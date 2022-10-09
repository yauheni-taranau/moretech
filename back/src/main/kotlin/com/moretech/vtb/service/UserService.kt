package com.moretech.vtb.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.moretech.vtb.controller.dto.LoginDto
import com.moretech.vtb.controller.dto.LoginResponseDto
import com.moretech.vtb.controller.dto.RegisterUserDto
import com.moretech.vtb.controller.dto.UserInfoDto
import com.moretech.vtb.entity.NFTWallet
import com.moretech.vtb.entity.Role
import com.moretech.vtb.entity.User
import com.moretech.vtb.entity.UserData
import com.moretech.vtb.repository.NFTWalletRepo
import com.moretech.vtb.repository.UserRepo
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder,
    private val objectMapper: ObjectMapper,
    private val nftService: NftService,
    private val nftWalletRepo: NFTWalletRepo,
    private val walletService: WalletService
) : UserDetailsService {

    fun getUser(username: String): User {
        return userRepo.findByUsername(username) ?: throw IllegalArgumentException("USER NOT FOUND")
    }


    fun getUserInfo(username: String): UserInfoDto {
        val user = getUser(username)
        val balance = walletService.getWalletBalance(user)
        return UserInfoDto(
                user.id!!,
                user.username,
                user.name,
                user.surname,
                balance!!.coinsAmount,
                user.roles
        )
    }

    fun save(user: User) = userRepo.save(user)
    fun saveUser(userDto: RegisterUserDto): User {
        if (userRepo.existsByUsername(userDto.username)) {
            throw IllegalArgumentException("USER EXISTS")
        } else {
            var user = User(
                name = userDto.name,
                password = passwordEncoder.encode(userDto.password),
                surname = userDto.surname,
                username = userDto.username,
                roles = mutableListOf(Role.USER),
                unit = null
            )
            user = userRepo.save(user)
            val walletDto = nftService.createWallet()!!
            val wallet = NFTWallet(
                null,
                walletDto.privateKey,
                walletDto.publicKey
            )
            nftWalletRepo.save(wallet)
            user.nftWallet = wallet
            userRepo.save(user)
            return user
        }
    }


    fun addRoleToUser(username: String, role: Role) {
        //TODO
        val user = userRepo.findByUsername(username)!!
        user.roles.add(role)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username) ?: throw IllegalArgumentException("USER NOT FOUND")
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            user.roles.map { SimpleGrantedAuthority(it.name) }
        )
    }

    fun login(loginDto: LoginDto): LoginResponseDto {
        val user = userRepo.findByUsername(loginDto.username) ?: throw BadCredentialsException("Wrong password")
        if (passwordEncoder.matches(loginDto.password, user.password)) {
            return LoginResponseDto(encryptUser(user))
        } else {
            throw BadCredentialsException("Wrong password")
        }
    }

    fun encryptUser(user: User): String {
        return Base64.getEncoder().encodeToString(
            objectMapper.writeValueAsBytes(
                UserData(
                    user.name,
                    user.surname,
                    user.username,
                    user.roles
                )
            )
        )
    }

    fun decryptUser(token: String): User {
        val userData = objectMapper.readValue(Base64.getDecoder().decode(token), UserData::class.java)
        return userRepo.findByUsername(userData.username)!!
    }

    fun addExp(user: User, amount: Long) = userRepo.save(user.apply {
        exp += amount
    }).exp
}