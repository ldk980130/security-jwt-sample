package com.spring.securityjwtsample.application

import com.spring.securityjwtsample.domain.Member
import com.spring.securityjwtsample.domain.MemberRepository
import com.spring.securityjwtsample.domain.Role
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class JoinService(
        val memberRepository: MemberRepository,
        val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    fun join(
            username: String,
            password: String,
            email: String,
            role: String
    ): Long {
        val encodedPassword = bCryptPasswordEncoder.encode(password)
        val member = Member(username, encodedPassword, email, Role.valueOf(role))
        memberRepository.save(member)
        return member.id!!
    }

    @PostConstruct
    fun init() {
        val password = bCryptPasswordEncoder.encode("1234")
        memberRepository.saveAll(
                listOf(
                        Member("user", password, "email@123.gmail.com", Role.USER),
                        Member("manager", password, "email@456.gmail.com", Role.MANAGER),
                        Member("admin", password, "email@567.gmail.com", Role.ADMIN)
                )
        )
    }
}