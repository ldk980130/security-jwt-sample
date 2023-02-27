package com.spring.securityjwtsample.security.auth

import com.spring.securityjwtsample.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

/**
 * 로그인이 완료가 되면 시큐리티 session 이 만들어진다. (Security ContextHolder)
 * Authentication 타입 객체 안에 UserDetails 타입 객체가 있다.
 * UserDetails 를 통해 User 정보를 얻어올 수 있다.
 *
 * UserDetail은 일반 로그인, OAuth 로그인을 하면 OAuth2User 타입이 Authentication 타입 객체에 들어간다.
 */
class PrincipalDetails(private val member: Member) : UserDetails, OAuth2User {

    private var attributes: MutableMap<String, Any> = mutableMapOf()

    constructor(member: Member, attributes: MutableMap<String, Any>) : this(member) {
        this.attributes = attributes
    }
    override fun getName(): String {
        return "name"
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return attributes
    }

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