package SilkLoad.config;

import SilkLoad.interceptor.ErrorPageCheckInterceptor;
import SilkLoad.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

      //에러 페이지 처리
        registry.addInterceptor(new ErrorPageCheckInterceptor())
                .order(0)
                .addPathPatterns("/**")
                .excludePathPatterns("/",
                        "/members/add", "/members/myPage/profile", "/members/myPage/wishlist", "/members/myPage/saleOrders",
                        "/members/myPage/purchaseOrders", "/product/order/transaction/**", "/members/myPage/myChatRoomList",
                        "/members/myPage/room/**",
                        "/login", "/logout", "/home","/loginMember","/notifications/**",
                        "/product/add", "/product/order/buyNow", "/product/order/inCart", "/product/images/**", "/product/order/buyAuction",
                        "/product/order/deleteCart",
                        "/Cart", "/myPage",
                        "/shop/**",
                        "/css/**", "/*.ico", "/error", "/vendor/**", "/img/**", "/js/**", "/fonts/**");

        //로그인 인증
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/members/add","/login", "/logout", "/home", "/product/images/**", "/shop/**","/loginMember",
                        "/css/**", "/*.ico", "/error", "/vendor/**", "/img/**", "/js/**", "/fonts/**"
                );

    }


}
