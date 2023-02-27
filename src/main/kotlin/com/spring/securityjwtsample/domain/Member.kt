package com.spring.securityjwtsample.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Member(
        val username: String,
        val password: String,
        val email: String,
        @Enumerated(EnumType.STRING)
        val role: Role
) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null

        @CreatedDate
        var createDate: LocalDateTime = LocalDateTime.MIN
}