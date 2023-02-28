package com.spring.securityjwtsample.security

import com.spring.securityjwtsample.domain.MemberRepository
import com.spring.securityjwtsample.domain.Role
import com.spring.securityjwtsample.security.auth.PrincipalOauth2UserService
import com.spring.securityjwtsample.security.jwt.JwtAuthorizationFilter
import com.spring.securityjwtsample.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // @Secured 어노테이션을 통해 url을 블락해준다.
class SecurityConfig(
    private val principalOauth2UserService: PrincipalOauth2UserService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        return http
            .csrf {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }

            .authorizeHttpRequests {
                it.requestMatchers("/api/user/**")
                    .hasAnyAuthority(Role.USER.name, Role.MANAGER.name, Role.ADMIN.name)
                it.requestMatchers("/api/manager/**")
                    .hasAnyAuthority(Role.MANAGER.name, Role.ADMIN.name)
                it.requestMatchers("/api/admin/**")
                    .hasAuthority(Role.ADMIN.name)
                it.anyRequest().permitAll()
            }
            .oauth2Login {
                it.userInfoEndpoint().userService(principalOauth2UserService)
                it.defaultSuccessUrl("/auth/login")
            }
            .addFilterBefore(
                JwtAuthorizationFilter(jwtTokenProvider, memberRepository),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

    /**
     * 스프링 MVC 단에서 cors 허용 처리를 할 수도 있지만
     * 인증이 필요해서 시큐리티를 거치게 되면 통하지 않기에
     * 시큐리티 단에서의 cors 처리가 필요하다.
     */
    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true // 내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 설정하는 것
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/api/**", config)
        return CorsFilter(source)
    }
}