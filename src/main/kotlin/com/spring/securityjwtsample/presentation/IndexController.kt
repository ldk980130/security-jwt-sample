package com.spring.securityjwtsample.presentation

import com.spring.securityjwtsample.security.auth.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController{

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/user")
    @ResponseBody
    fun user(@AuthenticationPrincipal principalDetails: PrincipalDetails): String {
        return principalDetails.username
    }

    @GetMapping("/admin")
    @ResponseBody
    fun admin(): String {
        return "admin"
    }

    @GetMapping("/manager")
    @ResponseBody
    fun manger(): String {
        return "manager"
    }

    @GetMapping("/loginForm")
    fun loginForm(): String {
        return "loginForm"
    }
}