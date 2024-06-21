package com.savmoney.co.kr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.savmoney.co.kr.Service.MemberService;
import com.savmoney.co.kr.dto.MemberDTO;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/savemoney/joinmember")
    public ResponseEntity<Void> postLogin(@RequestBody MemberDTO memberDTO, HttpServletResponse res) {
        
        memberService.joinMember(memberDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
  
    @PostMapping("/savemoney/login")
    public ResponseEntity<?> postJoinMember(@RequestBody Map<String, String> params, HttpServletResponse res) {
        
        // String user = params.get("email");
        // String password = params.get("password");

        return ResponseEntity.ok().body("test");
    }

}