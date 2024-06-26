package com.savemoney.co.kr.springsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        
        // 사용자 정보 얻기
        String username = authentication.getName();
        
        // 로그 추가
        System.out.println("로그인 성공: " + username);
        
        // 세션에 사용자 이름 저장
        request.getSession().setAttribute("username", username);

        // 클라이언트로부터 리다이렉트 URL 얻기 (필요시 쿼리 파라미터 또는 세션 사용)
        String redirectUrl = request.getParameter("redirectUrl");
        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = "http://localhost:3000"; // 기본 리다이렉트 URL
        }

        // 성공 후 리다이렉트
        response.sendRedirect(redirectUrl);

        // throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
    }
}
   