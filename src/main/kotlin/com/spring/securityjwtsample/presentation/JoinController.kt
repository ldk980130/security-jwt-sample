package com.spring.securityjwtsample.presentation

import com.spring.securityjwtsample.application.JoinService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class JoinController(val joinService: JoinService) {

    @GetMapping("/joinForm")
    fun joinForm(): String {
        return "joinForm"
    }

    @PostMapping("/join")
    @ResponseBody
    fun join(
            @RequestParam username: String,
            @RequestParam password: String,
            @RequestParam email: String
    ): String {
        val id = joinService.join(username, password, email)
        return "join $id"
    }

    @GetMapping("/joinProc")
    @ResponseBody
    fun joinProc(): String {
        return "회원가입 완료됨"
    }
}