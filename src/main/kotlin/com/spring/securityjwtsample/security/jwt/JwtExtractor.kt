package com.spring.securityjwtsample.security.jwt

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import java.util.*

private const val BEARER_TYPE: String = "Bearer"

class JwtExtractor {
    companion object {
        fun existBearerToken(request: HttpServletRequest): Boolean {
            val token = request.getHeader(HttpHeaders.AUTHORIZATION)
            return token != null
                    && token.lowercase(Locale.getDefault()).startsWith(BEARER_TYPE.lowercase())

        }
        fun extract(request: HttpServletRequest): String {
            if (existBearerToken(request)) {
                val token = request.getHeader(HttpHeaders.AUTHORIZATION)
                return token.substring(BEARER_TYPE.length).trim { it <= ' ' }
            }
            return ""
        }
    }
}