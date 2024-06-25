package com.savemoney.co.kr.controller;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/")
    public ResponseEntity<?> postJoinmember() {


        
        return ResponseEntity.status(HttpStatus.OK).body(0);

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
    public ResponseEntity<?> postloginMember(@RequestBody Map<String, String> params, HttpServletResponse res) {
        
        String chkId = memberService.memberLogin(params);
        String msg = null; // 데이터가 없을 경우 null

        Map<String, Object> response = new HashMap<>();

        try {



            //DB에 저장되어 있으면 로그인 처리 2024.06.23
            if (chkId == null) msg = "입력한 정보가 올바르지 않습니다.";  // 2024. 06. 23 wingerms 공통
            
            // 로그인 성공 시 jwt 넣기
            else{

                //id를 통해서 token값 얻기
                String token = jwtUtil.getToken("memberId", chkId);

                Cookie cookie = new Cookie("token", token);

                // XSS 방지
                cookie.setHttpOnly(true);
                
                cookie.setPath("/"); //해당 도메인의 모든 경로에서 쿠키가 유효하도록 설정하는 것을 의미
                res.addCookie(cookie);// JWT 쿠키에 넣기 

                response.put("memberId",chkId);

            }

            response.put("msg",msg);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            
            logger.debug("postJoinMember Controller Error",e);
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

        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);

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

}