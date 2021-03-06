package com.github.zemke.tippspiel2.view.controller

import com.github.zemke.tippspiel2.service.UserService
import com.github.zemke.tippspiel2.view.exception.NotFoundException
import com.github.zemke.tippspiel2.view.model.UserCreationDto
import com.github.zemke.tippspiel2.view.model.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserRestController(
        @Autowired private val userService: UserService
) {

    @PostMapping("")
    fun createUser(@RequestBody userCreationDto: UserCreationDto): ResponseEntity<UserDto> {
        val plainPassword = userCreationDto.password
        val persistedUser = userService.addUser(
                userCreationDto.firstName, userCreationDto.lastName,
                userCreationDto.email, userCreationDto.password)
        val token = userService.authenticate(persistedUser.email, plainPassword)

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserDto.toDto(persistedUser, token))
    }

    @GetMapping("/{id}")
    fun readUser(@PathVariable("id") id: Long): ResponseEntity<UserDto> {
        return when (userService.getUser(id)) {
            null -> throw NotFoundException("User with id $id not found.")
            else -> ResponseEntity.ok(UserDto.toDto(userService.getUser(id)!!))
        }
    }

    @GetMapping("")
    fun readUsers(): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok(userService.findAllUsers().map { UserDto.toDto(it) })
    }
}
