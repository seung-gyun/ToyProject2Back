package com.savmoney.co.kr.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class mainPageController {

    @GetMapping("/savemoney")
    public ResponseEntity<String> getMain() {
        
        String test = "return success";
        
        return new ResponseEntity<>(test, HttpStatus.OK);
    }
    

}
