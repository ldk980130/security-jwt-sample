package com.spring.securityjwtsample.security.auth

import com.spring.securityjwtsample.domain.Member
import com.spring.securityjwtsample.domain.MemberRepository
import com.spring.securityjwtsample.domain.Role
import com.spring.securityjwtsample.support.log
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * OAuth 로그인 후 후처리 되는 빈
 */
@Service
@Transactional
class PrincipalOauth2UserService(
        private val memberRepository: MemberRepository
) : DefaultOAuth2UserService() {

    /**
     * 구글로부터 받은 데이터에 대한 후처리
     * OAuth2UserRequest 안에 ID_Token이 존재
     * ID_Token을 디코딩하면 이름, 이메일, 프로필 등 정보가 들어 있다.
     */
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        validateRequestNull(userRequest)
        logging(userRequest)
        val oAuth2User = super.loadUser(userRequest)
        val email = oAuth2User.getAttribute<String>("email")
        val member = memberRepository.findByEmail(email!!)
                ?: join(oAuth2User, email)
        return PrincipalDetails(member, oAuth2User.attributes)
    }

    private fun join(oAuth2User: OAuth2User, email: String): Member {
        val name = oAuth2User.getAttribute<String>("name")!!
        val newMember = Member(name, "oauthPw", email, Role.USER)
        return memberRepository.save(newMember)
    }

    private fun validateRequestNull(userRequest: OAuth2UserRequest?) {
        if (userRequest == null) {
            throw IllegalStateException("구글에서 사용자 정보를 받아오지 못했습니다.")
        }
    }

    private fun logging(userRequest: OAuth2UserRequest?) {
        log().info("clientRegistration: {}", userRequest!!.clientRegistration)
        log().info("accessToken: {}", userRequest.accessToken)
        log().info("additionalParameters: {}", userRequest.additionalParameters)
    }
}