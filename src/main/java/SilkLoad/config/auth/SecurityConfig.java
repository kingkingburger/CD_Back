package SilkLoad.config.auth;

import SilkLoad.entity.UserRoleEnum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**","/fonts/**","/img/**","/vendor/**", "/js/**").permitAll() // 허용 파일 범위
                .antMatchers("/shop/**").permitAll()
                .antMatchers("/product/add").hasRole(Role.USER.name()) // username이 있는 사용자만 들어갈 수 있따.
                .anyRequest()
                .authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")//로그아웃이 성공하면 가는 곳
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                    .userService(customOAuth2UserService);
    }

}
