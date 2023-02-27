package com.spring.securityjwtsample.presentation

import com.spring.securityjwtsample.application.JoinService
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MemberController(val joinService: JoinService) {

    @GetMapping("/joinForm")
    fun joinForm(): String {
        return "joinForm"
    }

    @PostMapping("/join")
    @ResponseBody
    fun join(
            @RequestParam username: String,
            @RequestParam password: String,
            @RequestParam email: String,
            @RequestParam role: String
    ): String {
        val id = joinService.join(username, password, email, role)
        return "join $id $role"
    }

    @GetMapping("/info")
    @ResponseBody
    @Secured("ADMIN") // @EnableMethodSecurity(securedEnabled = true) 덕에 어노테이션 기반 블락 가능
    fun adminInfo(): String {
        return "관리자 개인 정보"
    }
}