package com.spring.securityjwtsample.security.auth

import com.spring.securityjwtsample.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * 로그인이 완료가 되면 시큐리티 session 이 만들어진다. (Security ContextHolder)
 * Authentication 타입 객체 안에 UserDetails 타입 객체가 있다.
 * UserDetails 를 통해 User 정보를 얻어올 수 있다.
 */
class PrincipalDetails(private val member: Member) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(member.role.name))
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}