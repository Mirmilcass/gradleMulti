package edu.mirmil.web.controller

import edu.mirmil.domain.entity.User
import edu.mirmil.web.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user/")
class UserController(private val userService: UserService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    fun created(@Validated(User.Created::class) @RequestBody user: User): User {
        return userService.create(user)
    }

    @PostMapping("login")
    fun login(@RequestBody user: User): String {
        return userService.login(user)
    }

    @PostMapping("findAll")
    fun findAll(): MutableList<User> {
        return userService.findAll()
    }

    @PostMapping("find/{idx}")
    fun findOne(@PathVariable idx: Long): Optional<User> {
        return userService.findOne(idx)
    }
}