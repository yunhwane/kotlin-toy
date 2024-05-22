package com.example.testcode.user.infrastucture.repository

import com.example.testcode.user.infrastucture.persistence.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository: JpaRepository<UserModel, Long> {
    fun findByEmail(email: String): UserModel?
}