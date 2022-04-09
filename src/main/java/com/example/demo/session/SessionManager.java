package com.example.demo.session;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션관리
 */
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    //여러 쓰레드가 동시에 접근하면 ConcurrentHashMap을 써야한다.
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();


    /**
     * 세션 생성
     */
    public void createdSession(Object value, HttpServletResponse response){

        //세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);
        System.out.println("sessionId = " + sessionId);
        //쿠키 생성
        Cookie mysessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mysessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie == null){
            System.out.println("getSession에서 세션을 못가져옴");

            return new IllegalStateException("getSession에서 세션을 못가져옴");
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request){
        Cookie sessioncookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessioncookie != null){
            sessionStore.remove(sessioncookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookiename){
//        Cookie[] cookies = request.getCookies();
        System.out.println("request = " + request.getCookies());
        if(request.getCookies() ==null){
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookiename))
                .findAny()
                .orElse(null);
    }

}
