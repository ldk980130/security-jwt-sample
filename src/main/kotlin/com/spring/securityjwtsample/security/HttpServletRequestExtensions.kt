package com.spring.securityjwtsample.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import java.util.*

private const val BEARER_TYPE: String = "Bearer"

fun HttpServletRequest.extractBearerToken(): String {
    if (this.existBearerToken()) {
        val token = this.getHeader(HttpHeaders.AUTHORIZATION)
        return token.substring(BEARER_TYPE.length).trim { it <= ' ' }
    }
    return ""
}
fun HttpServletRequest.existBearerToken(): Boolean {
    val token = this.getHeader(HttpHeaders.AUTHORIZATION)
    return token != null
            && token.lowercase(Locale.getDefault()).startsWith(BEARER_TYPE.lowercase())
}
