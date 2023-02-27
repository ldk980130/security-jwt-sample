package com.spring.securityjwtsample.presentation

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
    fun user(): String {
        return "user"
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