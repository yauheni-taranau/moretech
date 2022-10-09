package com.moretech.vtb.controller

import com.moretech.vtb.controller.dto.UserDto
import com.moretech.vtb.controller.dto.admin.AddUserToUnitDto
import com.moretech.vtb.controller.dto.admin.CreateUnitDto
import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.service.AdminService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
        private val adminService: AdminService
) {

    @PostMapping("/unit")
    fun createUnit(@RequestBody unit: CreateUnitDto) {
        adminService.createUnit(unit)
    }

    @GetMapping("/unit/current")
    fun getCurrentUnit(@RequestHeader(name = AuthUtils.AUTH_HEADER) token: String) = adminService.getCurrentUnit(token)

    @PostMapping("/unit/{id}/addUser")
    fun addUserToUnit(@PathVariable id: Long, @RequestBody addUserToUnitDto: AddUserToUnitDto) {
        adminService.addUserToUnit(id, addUserToUnitDto)
    }

    @PostMapping("/unit/{id}/transferRubles")
    fun transferRubles(@PathVariable id: Long, @RequestBody transferRequest: TransferRequest) = adminService.transferRublesFromUnit(id, transferRequest)


    @PostMapping("/unit/{id}/removeUser")
    fun removeUserFromUnit(@PathVariable id: Long, @RequestBody addUserToUnitDto: AddUserToUnitDto) {
        adminService.removeUserFromUnit(id, addUserToUnitDto)
    }

    @GetMapping("/unit/{id}/users")
    fun getAllUsersFromUnit(@PathVariable id: Long, @RequestBody addUserToUnitDto: AddUserToUnitDto): List<UserDto> {
        return adminService.getAllUsersFromUnit(id)
    }
}