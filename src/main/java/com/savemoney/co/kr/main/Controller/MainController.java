package com.savemoney.co.kr.main.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class MainController {
    
    @GetMapping("/savemoney")
    public ResponseEntity<Void> getMethodName() {
        return ResponseEntity.status(200).body(null);
    }
    

}