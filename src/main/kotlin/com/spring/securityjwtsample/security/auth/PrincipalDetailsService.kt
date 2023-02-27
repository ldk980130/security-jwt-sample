package com.spring.securityjwtsample.security.auth

import com.spring.securityjwtsample.domain.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * /login 요청이 오면 시큐리티가 낚아챈 뒤
 * UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행
 *
 * 기본적으로 POST /login 요청이 올 때 폼 형태로 넘어오는 파라미터 이름이 'username'이어야 동작한다.
 * 커스텀하려면 SecurityChain을 만들 때 http.usernamePrameter() 로 변경할 수 있다.
 */
@Service
class PrincipalDetailsService(
        private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        validateUsernameBlank(username)
        val member = memberRepository.findByUsername(username!!)
                ?: throw IllegalStateException("해당 username에 해당하는 회원이 없습니다.")
        return PrincipalDetails(member)
    }

    private fun validateUsernameBlank(username: String?) {
        if (username.isNullOrBlank()) {
            throw IllegalArgumentException("Username은 비어있을 수 없습니다.")
        }
    }
}