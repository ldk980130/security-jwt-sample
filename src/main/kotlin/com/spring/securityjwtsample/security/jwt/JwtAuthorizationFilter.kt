package com.spring.securityjwtsample.security.jwt

import com.spring.securityjwtsample.domain.MemberRepository
import com.spring.securityjwtsample.security.SecurityUtil
import com.spring.securityjwtsample.security.auth.PrincipalDetails
import com.spring.securityjwtsample.security.existBearerToken
import com.spring.securityjwtsample.security.extractBearerToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

/**
 * 시큐리티가 가진 필터 중 BasicAuthenticationFilter가 있다.
 * 권한이나 인증이 필요한 경우 위 필터를 무조건 탄다.
 * 만약 권한이나 인증이 필요한게 아니면 위 필터를 타지 않는다.
 */
class JwtAuthorizationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        if (request.existBearerToken()) {
            val accessToken = request.extractBearerToken()
            authorize(accessToken)
        }
        chain.doFilter(request, response)
    }

    private fun authorize(accessToken: String) {
        if (jwtTokenProvider.isValidToken(accessToken)) {
            val memberId = jwtTokenProvider.getId(accessToken)
            val member = memberRepository.findById(memberId).orElseThrow()
            SecurityUtil.authorize(PrincipalDetails(member))
        }
    }
}