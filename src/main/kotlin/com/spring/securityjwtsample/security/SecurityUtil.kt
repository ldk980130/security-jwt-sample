package com.spring.securityjwtsample.security

import com.spring.securityjwtsample.security.auth.PrincipalDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

class SecurityUtil {

    companion object {
        fun authorize(principalDetails: PrincipalDetails) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    principalDetails,
                    null,
                    principalDetails.authorities
                )
        }
    }
}