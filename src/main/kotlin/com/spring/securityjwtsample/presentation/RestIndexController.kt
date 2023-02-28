package com.spring.securityjwtsample.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestIndexController {

    @GetMapping("/api/home")
    fun home(): String {
        return "<h2>HOME</h2>"
    }

    @GetMapping("/api/user")
    fun user(): String {
        return "user"
    }

    @GetMapping("/api/manager")
    fun manager(): String {
        return "manager"
    }

    @GetMapping("/api/admin")
    fun admin(): String {
        return "admin"
    }
}