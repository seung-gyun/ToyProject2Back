package com.savemoney.co.kr.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.savemoney.co.kr.jwtUtil.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Autowired
        JwtUtil jwtUtil;

        private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        
        // 성공후 갈 페이지
        private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    
        @Override
        public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException, ServletException {
        
        SecurityContext securityContext = this.securityContextHolderStrategy.getContext();
		securityContext.setAuthentication(authentication);
		this.securityContextHolderStrategy.setContext(securityContext);
		this.securityContextRepository.saveContext(securityContext, req, res);

        String username = authentication.getName();

        // 성공 시 redirect하기 위해서
        SavedRequest savedRequest = requestCache.getRequest(req, res);
        
        // 이전에 실패한 url이 있었다는 것
        String referrer = req.getHeader("referer");
        String goToUrl="";

        if (savedRequest != null) {

            goToUrl = savedRequest.getRedirectUrl().toString();

            if(referrer.equals(goToUrl)){
            
                Pattern pattern = Pattern.compile("/board");
                Matcher matcher = pattern.matcher(goToUrl);
            
                if (matcher.find()) {
                    goToUrl = matcher.group(0); // "board" 값을 추출
                }

            }
            else{
                goToUrl = "";
            }

        }  

        //id를 통해서 token값 얻기
        String token = jwtUtil.getToken("memberId", username);
        Cookie cookie = new Cookie("token", token);

        // XSS 방지
        cookie.setHttpOnly(true);
        
        cookie.setPath("/"); //해당 도메인의 모든 경로에서 쿠키가 유효하도록 설정하는 것을 의미
        res.addCookie(cookie);// JWT 쿠키에 넣기 

        //JSON 형태로 반환
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write("{\"username\": \"" + username + "\", \"redirectUrl\": \"" + goToUrl + "\"}");
        res.getWriter().flush();

        }            

    }
   