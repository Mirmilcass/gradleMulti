package edu.mirmil.web.service

import edu.mirmil.domain.entity.User
import edu.mirmil.domain.repository.UserRepository
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    @Transactional
    override fun create(user: User): User {
        return userRepository.save(user)
    }

    override fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

    override fun findOne(idx: Long): Optional<User> {
        return userRepository.findById(idx)
    }

    override fun login(user: User): User {
        return userRepository.findOne(Example.of(user)).orElseThrow {
            throw Exception("가입되지 않은 계정입니다.")
        }
    }
}