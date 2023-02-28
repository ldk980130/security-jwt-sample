package com.spring.securityjwtsample.security.auth

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

/**
 * OAuth 로그인 후 후처리 되는 빈
 */
@Service
class PrincipalOauth2UserService: DefaultOAuth2UserService() {

    /**
     * 구글로부터 받은 데이터에 대한 후처리
     * OAuth2UserRequest 안에 ID_Token이 존재
     * ID_Token을 디코딩하면 이름, 이메일, 프로필 등 정보가 들어 있다.
     */
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        validateRequestNull(userRequest)
        return super.loadUser(userRequest)
    }


    private fun validateRequestNull(userRequest: OAuth2UserRequest?) {
        if (userRequest == null) {
            throw IllegalStateException("구글에서 사용자 정보를 받아오지 못했습니다.")
        }
    }
}