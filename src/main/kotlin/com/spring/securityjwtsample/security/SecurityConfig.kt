package com.spring.securityjwtsample.security

import com.spring.securityjwtsample.domain.Role
import com.spring.securityjwtsample.security.auth.PrincipalOauth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // @Secured 어노테이션을 통해 url을 블락해준다.
class SecurityConfig(
        private val principalOauth2UserService: PrincipalOauth2UserService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        return http
                .csrf().disable()

                .authorizeHttpRequests()
                .requestMatchers("/user").authenticated()
                .requestMatchers("/manager/**").hasAnyAuthority(Role.MANAGER.name, Role.ADMIN.name)
                .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name)
                .anyRequest().permitAll()
                .and()

                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // POST /login이 호출되면 시큐리티가 낚아채서 대신 로그인 진행
                .defaultSuccessUrl("/")
                .and()

                // <a href="/oauth2/authorization/google">구글 로그인</a> 누르면 구글 로그인 시작
                .oauth2Login()
                // 구글 로그인 완료 후 후처리가 필요함
                // 1.코드 받기
                // 2.코드로 엑세스토큰 + 사용자 정보(프로필 등) 가져오기
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                .and()

                .and()
                .build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}