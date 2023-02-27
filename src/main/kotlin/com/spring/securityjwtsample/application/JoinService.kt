package com.spring.securityjwtsample.application

import com.spring.securityjwtsample.domain.Member
import com.spring.securityjwtsample.domain.MemberRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JoinService(
        val memberRepository: MemberRepository,
        val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @Transactional
    fun join(
            username: String,
            password: String,
            email: String
    ): Long {
        val encodedPassword = bCryptPasswordEncoder.encode(password)
        val member = Member(username, encodedPassword, email, "ROLE_USER")
        memberRepository.save(member)
        return member.id!!
    }
}