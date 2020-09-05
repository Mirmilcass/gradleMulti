package edu.mirmil.web.service

import edu.mirmil.domain.entity.User
import edu.mirmil.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun create(user: User): User {
        return userRepository.save(user)
    }

    override fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

    override fun findOne(idx: Long): Optional<User> {
        return userRepository.findById(idx)
    }
}