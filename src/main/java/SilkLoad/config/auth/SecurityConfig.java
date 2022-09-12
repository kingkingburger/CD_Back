package SilkLoad.config.auth;

import SilkLoad.entity.UserRoleEnum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


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
                .antMatchers("/product/**").hasRole(Role.GUEST.name()) // guest 사용자만 들어갈 수 있따. 들어온 String 뒤에 자동으로 _ROLE 붙여준다.
                .antMatchers("/members/**").hasRole(Role.GUEST.name()) // username이 있는 사용자만 들어갈 수 있따.
                .antMatchers("/chat/**").hasRole(Role.GUEST.name()) // username이 있는 사용자만 들어갈 수 있따.
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
