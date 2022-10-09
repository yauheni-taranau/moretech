package com.moretech.vtb.service

import com.moretech.vtb.client.dto.TransactionResponse
import com.moretech.vtb.controller.dto.UserDto
import com.moretech.vtb.controller.dto.admin.AddUserToUnitDto
import com.moretech.vtb.controller.dto.admin.CreateUnitDto
import com.moretech.vtb.controller.dto.admin.UnitDto
import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.entity.NFTWallet
import com.moretech.vtb.entity.Role
import com.moretech.vtb.entity.Unit
import com.moretech.vtb.entity.User
import com.moretech.vtb.repository.NFTWalletRepo
import com.moretech.vtb.repository.UnitRepo
import org.springframework.stereotype.Service

@Service
class AdminService(
        private val unitRepo: UnitRepo,
        private val userService: UserService,
        private val nftService: NftService,
        private val nftWalletRepo: NFTWalletRepo,
        private val walletService: WalletService
) {

    fun createUnit(unit: CreateUnitDto) {
        val walletDto = nftService.createWallet()!!
        val wallet = NFTWallet(
                null,
                walletDto.privateKey,
                walletDto.publicKey
        )
        nftWalletRepo.save(wallet)
        val manager = userService.getUser(unit.managerUsername)
        if (manager.roles.contains(Role.MANAGER)) {
            unitRepo.save(Unit(name = unit.name, manager = manager, nftWallet = wallet))
        } else {
            throw IllegalArgumentException("user is not manager")
        }
    }

    fun getCurrentUnit(token: String): UnitDto {
        val user = userService.decryptUser(token)
        val unit = unitRepo.findByManagerId(user.id!!)
        val balance = walletService.getWalletBalance(unit.nftWallet!!)!!
        return UnitDto(
                unit.id!!,
                unit.name,
                balance.coinsAmount,
                unit.users.map { UserDto(it.id!!, it.name, it.surname, it.username) }
        )
    }

    fun addUserToUnit(id: Long, addUserToUnitDto: AddUserToUnitDto) {
        val unit = unitRepo.findById(id).get()
        val user = userService.getUser(addUserToUnitDto.username)
        if (user.unit == null) {
            user.unit = unit
            userService.save(user)
        } else {
            throw IllegalArgumentException("User had group already")
        }
    }

    fun removeUserFromUnit(id: Long, addUserToUnitDto: AddUserToUnitDto) {
        val unit = unitRepo.findById(id).get()
        val user = userService.getUser(addUserToUnitDto.username)
        if (user.unit != null && user.unit!!.id == unit.id) {
            user.unit = null
            userService.save(user)
        } else {
            throw IllegalArgumentException("User had no group")
        }
    }

    fun transferRublesFromUnit(unitId: Long, transferRequest: TransferRequest): TransactionResponse? {
        val unit = unitRepo.findById(unitId).get()
        return walletService.transferRubles(unit.nftWallet!!, transferRequest)
    }

    fun getAllUsersFromUnit(id: Long): List<UserDto> {
        val unit = unitRepo.findById(id).get()
        return unit.users.map { UserDto(it.id!!, it.name, it.surname, it.username) }
    }
}