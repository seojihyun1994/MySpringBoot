package com.shopping.config;

import com.shopping.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 설정 파일로 사용할게요.
@EnableWebSecurity // 스프링 시큐리티를 사용할게요.
public class SecurityConfig extends WebSecurityConfigurerAdapter { // SecurityConfig01
    @Autowired
    MemberService memberService ; // SecurityConfig02

    @Override // HttpSecurity는 http 요청에 대하여 페이지 권한, 로그인 페이지 설정 등등을 적용시켜주는 클래스스
    protected void configure(HttpSecurity http) throws Exception { // 연쇄적으로 적는것, 메소드 체이닝 // SecurityConfig01

        // SecurityConfig02
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email") // 로그인한 사람들을 email로 식별하겠다.
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/") ;

        // SecurityConfig03
        // authorizeRequests() 메소드는 시큐리티 처리시 HttpServeletRequests를 사용하고자 할 때 사용합니다.
        http.authorizeRequests()
                .mvcMatchers("/", "/members/**", "/products/**", "/images/**").permitAll() // 이쪽에 들어올 OK 할게요~
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() ;

        // SecurityConfig03
        // 허가 받지 않은 사용자가 특정 리소스에 접근시 감시(모니터링) 할게요.
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) ;

    }

    // AuthenticationManagerBuilder 는 AuthenticationManagerBuilder 객체를 생성해주는 역할을 합니다.
    // 인증 관리자 (AuthenticationManager) 객체에 암호화 및 사용자 정보(UserDetailsService) 및 암호화 설정을 지정해 줍니다.

    @Override // SecurityConfig02
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    // SecurityConfig03
    @Override
    public void configure(WebSecurity web) throws Exception{
        // static file(js, css, 이미지 등등)에 대해서는 그냥 무시하겠습니다.
        web.ignoring().antMatchers("/css/**","/js/**","/img/**") ;
    }

    @Bean // 해당 메소드 이름을 객체로 생성할 겁니다. // SecurityConfig01
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
