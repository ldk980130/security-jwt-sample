package com.spring.securityjwtsample.presentation

import com.spring.securityjwtsample.application.AuthService
import com.spring.securityjwtsample.security.jwt.JwtResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: AuthService) {

    @GetMapping("/auth/login")
    fun login(@AuthenticationPrincipal oAuth2User: OAuth2User): ResponseEntity<JwtResponse> {
        return ResponseEntity.ok(authService.login(oAuth2User))
    }
}