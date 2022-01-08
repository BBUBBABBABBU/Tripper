package com.tripper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author HanJiyoung
 * Security 관련 설정 클래스
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired private UserDetailsService jwtUserDetailsService;
    @Autowired private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 인증을 무시할 경로 설정하는 함수
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "h2-console/**");

        /* swagger */
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/swagger-ui/**",
                "/v2/**", "/swagger**");
    }

    /**
     * http 관련 인증 설정하는 함수
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeRequests()
                /* 인증 필요 없는 요청 */

                .antMatchers("/", "/signup", "/id-check", "/login", "/stomp/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .and()
                .authorizeRequests()
                /* 관리자 접근 가능 요청 */
                .antMatchers("/admin/hotel", "/admin/hotel/**"
                        , "/admin/roomtype", "/admin/roomtype/**"
                        , "/admin/room", "/admin/room/**", "/admin/photo/**"
                        , "/admin/reservations", "/admin/reservations/**").hasAnyAuthority("ADMIN", "MANAGER")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/users", "/admin/auth/**").hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /* 모든 요청에 대해 토큰을 인증 하는 필터 추가 */
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}