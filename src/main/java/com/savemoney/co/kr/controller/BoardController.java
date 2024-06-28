package com.savemoney.co.kr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.savemoney.co.kr.dto.BoardDTO;
import com.savemoney.co.kr.service.BoardService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    BoardService boardService;

    @GetMapping("/savemoney/board")
    public ResponseEntity<?> getMethodName() {
        
        try {
            
            List<BoardDTO> boardList = new ArrayList<>();
            boardList = boardService.boardList();
            return ResponseEntity.status(HttpStatus.OK).body(boardList);

        } catch (Exception e) {
            
            logger.debug("login Controller Error", e);
            throw e;
            
        }

    }
    

}
