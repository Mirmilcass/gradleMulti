package edu.mirmil.web.service

import edu.mirmil.domain.entity.User
import java.util.*

interface UserService {
    fun create(user: User): User
    fun findAll(): MutableList<User>
    fun findOne(idx: Long): Optional<User>
    fun login(user: User): String
}
