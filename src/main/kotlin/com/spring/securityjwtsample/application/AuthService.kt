package com.spring.securityjwtsample.application

import com.spring.securityjwtsample.domain.Member
import com.spring.securityjwtsample.domain.MemberRepository
import com.spring.securityjwtsample.domain.Role
import com.spring.securityjwtsample.security.jwt.JwtResponse
import com.spring.securityjwtsample.security.jwt.JwtTokenProvider
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Transactional
    fun login(oAuth2User: OAuth2User): JwtResponse {
        val email = oAuth2User.getAttribute<String>("email")
        val member = memberRepository.findByEmail(email!!)
            ?: join(oAuth2User, email)
        return JwtResponse(createToken(member))
    }

    private fun join(oAuth2User: OAuth2User, email: String): Member {
        val name = oAuth2User.getAttribute<String>("name")!!
        val newMember = Member(name, "oauthPw", email, Role.USER)
        return memberRepository.save(newMember)
    }

    private fun createToken(member: Member): String {
        val payload = mutableMapOf<String, Any>()
        payload["id"] = member.id!!
        payload["role"] = member.role.name
        return jwtTokenProvider.createToken(payload)
    }
}