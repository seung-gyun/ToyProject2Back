package com.savemoney.co.kr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.savemoney.co.kr.dto.MemberDTO;
import com.savemoney.co.kr.jwtUtil.JwtUtil;
import com.savemoney.co.kr.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    MemberService memberService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/savemoney/joinmember")
    public ResponseEntity<String> postJoinmember(@RequestBody MemberDTO memberDTO, HttpServletResponse res) {
        
        try {
            
            //아이디 중복체크
            String id = memberDTO.getMemberId();
            
            //초기화
            String msg = "";                

            id = memberService.findId(id);
            
            if(id!=null)                                  //D DB에서 가져오지 못하면 null처리가 난다.
                msg = "중복된 아이디가 존재합니다.";        // wingers 2024.06.22 공통으로 만들자.
            else{

                // 회원가입시 토큰 발급 후 메인페이지 이동
                String token = jwtUtil.getToken("id", id); // Jwt 토큰 발급
                Cookie cookie = new Cookie("token", token); // Jwt 비교를 쿠키값 비교
                cookie.setHttpOnly(true); // Xss 예방

                res.addCookie(cookie);

                memberService.joinMember(memberDTO);
            }

            return ResponseEntity.status(HttpStatus.OK).body(msg);

        } catch (Exception e) {

            logger.error("login Controller Error",e);
            throw e;

        }
        
    }
  
    @PostMapping("/savemoney/login")
    public ResponseEntity<?> postJoinMember(@RequestBody Map<String, String> params) {
        
        String chkId = memberService.memberLogin(params);
        String msg = null; // 데이터가 없을 경우 null

        Map<String, Object> response = new HashMap<>();

        try {

            //DB에 저장되어 있으면 로그인 처리 2024.06.23
            if (chkId == null) msg = "입력한 정보가 올바르지 않습니다.";  // 2024. 06. 23 wingerms 공통
            
            response.put("memberId",chkId);
            response.put("msg",msg);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            
            logger.debug("postJoinMember Controller Error",e);
            throw e;

        }

                
    }

    @GetMapping("/savemoney/findpwd")
    public ResponseEntity<?> getFindPwd(@RequestParam String memberId, @RequestParam String email, HttpServletResponse res) {
        
        return ResponseEntity.ok().body("test");
    }    

}