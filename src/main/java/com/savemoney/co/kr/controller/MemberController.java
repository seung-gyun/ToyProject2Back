package com.savemoney.co.kr.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.savemoney.co.kr.dto.MemberDTO;
import com.savemoney.co.kr.jwtUtil.JwtUtil;
import com.savemoney.co.kr.service.MemberService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    MemberService memberService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/savemoney/login")
	public Void test(HttpServletResponse res) throws IOException {

        res.sendRedirect("http://localhost:3000/");
        return null;

    }

    @PostMapping("/savemoney/joinmember")
    public ResponseEntity<String> postJoinmember(@RequestBody MemberDTO memberDTO, HttpServletResponse res) {
        
        try {
            
            //아이디 중복체크
            String id = memberDTO.getMemberId();
            
            //초기화
            String msg = "";                

            id = memberService.findDuple(id); //중복체크
            
            if(id!=null)                                  //D DB에서 가져오지 못하면 null처리가 난다.
                msg = "중복된 아이디가 존재합니다.";        // wingers 2024.06.22 공통으로 만들자.
            else{

                // 회원가입시 토큰 발급 후 메인페이지 이동
                // String token = jwtUtil.getToken("id", id); // Jwt 토큰 발급
                // Cookie cookie = new Cookie("token", token); // Jwt 비교를 쿠키값 비교
                // cookie.setHttpOnly(true); // Xss 예방

                // res.addCookie(cookie);

                memberService.joinMember(memberDTO);
            }

            return ResponseEntity.status(HttpStatus.OK).body(msg);

        } catch (Exception e) {

            logger.error("login Controller Error",e);
            throw e;

        }
        
    }

    @GetMapping("/savemoney/check")
    public ResponseEntity<?> getChk(@CookieValue(value="token", required = false) String token) { // cookie에 토큰값 확인
        
        Claims claims = jwtUtil.getClaims(token);

		if(claims!=null){

			String memberId = claims.get("memberId").toString();
			return ResponseEntity.status(HttpStatus.OK).body(memberId);
		}

		return ResponseEntity.ok().body(0);
    }    

    @PostMapping("/savemoney/logout")
	public ResponseEntity<?> logout(HttpServletResponse res) {

        Cookie tokenCookie = new Cookie("token", null);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);

        Cookie JseesionIdCookie = new Cookie("JSESSIONID", null);
        JseesionIdCookie.setPath("/");
        JseesionIdCookie.setMaxAge(0);

        res.addCookie(tokenCookie);
        res.addCookie(JseesionIdCookie);
        
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PatchMapping("/savemoney/mypage/id={memberId}")
	public ResponseEntity<?> updateMember(@PathVariable String memberId, @RequestBody MemberDTO memberDTO) {
        
        try {
        
            memberService.updateMember(memberDTO);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {

            logger.debug("updateMember Controller Error",e);
            throw e;
        }

        

    }

    @GetMapping("/savemoney/findId")
    public ResponseEntity<?> getfindId(@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber) { 
        
        try {
        
            String memberId = memberService.findId(email, phoneNumber);

            if(memberId!=null){
                return ResponseEntity.ok().body(memberId);    
            }else{
                return ResponseEntity.ok().body(0); // 우선은 성공처리
            }

            

        } catch (Exception e) {
            
            logger.debug("getfindId Controller Error",e);
            throw e;

        }
		
    }

    @GetMapping("/savemoney/findPwd")
    public ResponseEntity<?> getfindPwd(@RequestParam("memberId") String memberId, @RequestParam("email") String email) { 
        
        try {
        
            String chkResetPwd = memberService.findPwd(memberId, email);

            if(chkResetPwd!=null){
                return ResponseEntity.ok().body(chkResetPwd);    
            }else{
                return ResponseEntity.ok().body(0); // 우선은 성공처리
            }

            

        } catch (Exception e) {
            
            logger.debug("getfindPwd Controller Error",e);
            throw e;

        }
		
    }

    @DeleteMapping("/savemoney/mypage/memberdelete={memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable String memberId, HttpServletResponse res) { 
        
        try {
        
            memberService.deleteMember(memberId);

            //쿠키와 정보 삭제해줘야 함
            Cookie tokenCookie = new Cookie("token", null);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(0);
    
            Cookie JseesionIdCookie = new Cookie("JSESSIONID", null);
            JseesionIdCookie.setPath("/");
            JseesionIdCookie.setMaxAge(0);
    
            res.addCookie(tokenCookie);
            res.addCookie(JseesionIdCookie);
            
            return ResponseEntity.status(HttpStatus.OK).body(0);            

        } catch (Exception e) {
            
            logger.debug("getfindPwd Controller Error",e);
            throw e;

        }
		
    }

}