package com.spring.securityjwtsample.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

const val TWELVE_HOURS_IN_MILLISECONDS: Long = 1000 * 60 * 60 * 12

@Component
class JwtTokenProvider(
    private val signingKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256),
    private val expirationInMilliseconds: Long = TWELVE_HOURS_IN_MILLISECONDS
) {

    fun createToken(payload: Map<String, Any>): String {
        val now = Date()
        val validity = Date(now.time + expirationInMilliseconds)
        return Jwts.builder()
            .addClaims(payload)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getPayload(token: String): Long {
        return parseClaimsJws(token)
            .body
            .get("id", Long::class.java)
    }

    private fun parseClaimsJws(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
    }

    fun isValidToken(token: String): Boolean {
        return try {
            !parseClaimsJws(token).body.expiration.before(Date())
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}