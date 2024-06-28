package com.savemoney.co.kr.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HeaderController {

    @GetMapping("/savemoney/gohome")
    public ResponseEntity<?> getMethodName() {
        
        return new ResponseEntity<>(HttpStatus.OK);

    }
    

}
