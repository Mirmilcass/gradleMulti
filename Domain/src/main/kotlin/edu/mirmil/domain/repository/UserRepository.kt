package edu.mirmil.domain.repository

import edu.mirmil.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>