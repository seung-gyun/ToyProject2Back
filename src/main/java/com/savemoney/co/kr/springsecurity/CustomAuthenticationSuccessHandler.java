package com.savemoney.co.kr.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.savemoney.co.kr.jwtUtil.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Autowired
        JwtUtil jwtUtil;

    
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse res, Authentication authentication) throws IOException, ServletException {
        
        //성공했으니 authentication을 통해서 아이디 가져올 수 있다.
        String username = authentication.getName();
        
        // 로그인 성공 시 jwt 넣기

        //id를 통해서 token값 얻기
        String token = jwtUtil.getToken("memberId", username);
        Cookie cookie = new Cookie("token", token);

        // XSS 방지
        cookie.setHttpOnly(true);
        
        cookie.setPath("/"); //해당 도메인의 모든 경로에서 쿠키가 유효하도록 설정하는 것을 의미
        res.addCookie(cookie);// JWT 쿠키에 넣기 

        //JSON 형태로 반환
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(username);
        res.getWriter().flush();

        }            

    }
   