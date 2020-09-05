package edu.mirmil.web.controller

import edu.mirmil.domain.entity.User
import edu.mirmil.web.service.UserService
import io.swagger.annotations.*
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import java.util.*

@RestController
@RequestMapping("/user/")
@Api(description = "사용자 관리")
class UserController(private val userService: UserService) {

    @ApiOperation(
        value = "사용자 등록",
        notes = "사용자 등록을 진행합니다.\nid, password, name를 받습니다.\npassword의 경우 AES방식으로 암호화 하여 저장합니다.",
        response = User::class,
        produces = "Application/json"
    )
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(name = "id", required = true, dataType = "String"),
            ApiImplicitParam(name = "pass", value = "AES Crypt Save", required = true, dataType = "String"),
            ApiImplicitParam(name = "name", required = true, dataType = "String")
        ]
    )
    @PostMapping("create")
    fun created(user: User): User {
        return userService.create(user)
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