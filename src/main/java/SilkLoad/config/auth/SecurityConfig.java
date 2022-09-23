package SilkLoad.config.auth;

import SilkLoad.entity.UserRoleEnum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //인증 매니저 Bean으로 생성
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    //커스텀한 인증 방식 Override
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**","/fonts/**","/img/**","/vendor/**", "/js/**","/product/images/**").permitAll() // 허용 파일 범위
                .antMatchers("/shop/**").permitAll()
                .antMatchers("/members/add").permitAll()
                .antMatchers("/loginMember").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/product/**").hasRole(Role.GUEST.name()) // guest 사용자만 들어갈 수 있따. 들어온 String 뒤에 자동으로 _ROLE 붙여준다.
                .antMatchers("/members/**").hasRole(Role.GUEST.name()) // username이 있는 사용자만 들어갈 수 있따.
                .antMatchers("/chat/**").hasRole(Role.GUEST.name()) // username이 있는 사용자만 들어갈 수 있따.
                .anyRequest()
                .authenticated()
                .and()
                    .formLogin()//form으로 로그인 하려면 필수
                        .loginPage("/loginMember") // 로그인 하려는 페이지
                        .loginProcessingUrl("/login")//스프링 시큐리티가 해당 주소로 요청오는 로그인정보를 가로채서 loadUserByName으로 던짐
                        .loginProcessingUrl("/") //정상일때
                        .usernameParameter("loginId") //스프링 시큐리티 username을 loginId 로 받게끔
                .and()
                    .logout()
                        .logoutSuccessUrl("/")//로그아웃이 성공하면 가는 곳
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                    .userService(customOAuth2UserService);
    }

}
