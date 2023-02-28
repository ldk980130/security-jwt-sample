package com.spring.securityjwtsample.security

import com.spring.securityjwtsample.security.auth.PrincipalDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

class SecurityUtil {

    companion object {
        fun authorize(principalDetails: PrincipalDetails) {
            val context = SecurityContextHolder.createEmptyContext()
            context.authentication = UsernamePasswordAuthenticationToken(
                principalDetails,
                null,
                principalDetails.authorities
            )
            SecurityContextHolder.setContext(context)
        }
    }
}