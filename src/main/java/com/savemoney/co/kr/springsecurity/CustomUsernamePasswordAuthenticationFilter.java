package com.savemoney.co.kr.springsecurity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        // setFilterProcessesUrl("/savemoney/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();

        
        try {
            // Map<String, String> authRequest = objectMapper.readValue(request.getInputStream(), Map.class);
            Map<String, String> authRequest = objectMapper.readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});

            // 해당 경고 메시지는 Map 객체를 제네릭 타입으로 변환할 때 발생하는 타입 안전성 문제를 지적하는 것입니다. 이는 objectMapper.readValue(request.getInputStream(), Map.class) 코드가 제네릭 타입 정보 없이 Map 객체를 생성하기 때문에 발생합니다.

            String username = authRequest.get("username");
            String password = authRequest.get("password");

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            return this.getAuthenticationManager().authenticate(authToken);
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    
}