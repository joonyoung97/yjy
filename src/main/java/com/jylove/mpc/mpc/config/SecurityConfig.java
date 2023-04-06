package com.jylove.mpc.mpc.config;

import com.jylove.mpc.mpc.service.AdMemberManageService;
import com.jylove.mpc.mpc.service.MemberService;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired // 필요한 의존 객체의 타입에 해당하는 빈을 찾아 주입
    MemberService memberService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
        // userDetailsService = Spring Security 에서 유저의 정보를 가져오는 인터페이스
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 로그인 페이지 설정
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and().logout() // 로그아웃 페이지 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

        http.authorizeRequests()
                .mvcMatchers("/", "/members/**","/bclist/**","/images/**","/user/**","/wordCup/**")
                .permitAll().mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        //요청 주소에 따른 권한 설정 - mvcMatchers는 권한인증 없이 접속 가능함
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 비밀번호를 데이터 베이스에 그대로 저장했을경우 해킹 당하면 비밀번호가 그대로 노출된다.
        // 해시함수를 통해서 비밀번호를 암호화 하여 저장 하도록 한다.
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**","/image/**");
        // static 디렉토리 하위 파일에 대해 인증 없이 접근 가능 하게 설정
    }
}