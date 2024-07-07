package com.savemoney.co.kr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.savemoney.co.kr.dto.BoardDTO;
import com.savemoney.co.kr.service.BoardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    BoardService boardService;

    @GetMapping("/savemoney/board/{currentPage}")
    public ResponseEntity<?> getNoticePage(@PathVariable int currentPage) {
        
        try {
            
            String memberId = null;

            Map<String, Object> boardListPage = new HashMap<>();
            List<BoardDTO> boardList = new ArrayList<>();

            int size = 10;

            int totalPage = boardService.totalBoardList(memberId);
            int firstSize = (currentPage - 1) * size + 1;
            int lastSize = currentPage * size;

            boardListPage.put("totalPage", totalPage);
            boardListPage.put("firstSize", firstSize);
            boardListPage.put("lastSize", lastSize);

            boardList = boardService.boardList(firstSize, lastSize, memberId);
            boardListPage.put("boardList", boardList);

            return ResponseEntity.status(HttpStatus.OK).body(boardListPage);

        } catch (Exception e) {
            
            logger.debug("login Controller Error", e);
            throw e;
            
        }

    }
    
    @GetMapping("/savemoney/goregister")
    public ResponseEntity<?> getGoRegister() {
        
        try {
            


            return ResponseEntity.status(HttpStatus.OK).body(0);

        } catch (Exception e) {
            
            logger.debug("goregister Controller Error", e);
            throw e;
            
        }

    }

    @DeleteMapping("/savemoney/deletenotice/boardid={boardid}")
    public ResponseEntity<?> DeleteRegisterNotice(@PathVariable Long boardid) {
        
        try {
            
            boardService.deleteNotice(boardid);

            return ResponseEntity.status(HttpStatus.OK).body(0);

        } catch (Exception e) {
            
            logger.debug("DeleteRegisterNotice Controller Error", e);
            throw e;
            
        }

    }

    @PatchMapping("/savemoney/updatenotice/boardid={boardid}")
    public ResponseEntity<?> PatchRegisterNotice(@PathVariable Long boardid, @RequestBody Map<String, Object> noticeData) {
        
        try {
            
            BoardDTO boardDTO = new BoardDTO();
            
            String title = noticeData.get("title").toString();
            String content = noticeData.get("content").toString();
            String memberId = noticeData.get("memberId").toString();

            boardDTO.setBoardId(boardid);
            boardDTO.setTitle(title);
            boardDTO.setContent(content);
            boardDTO.setMemberId(memberId);

            boardService.updateNotice(boardDTO);


            return ResponseEntity.status(HttpStatus.OK).body(0);

        } catch (Exception e) {
            
            logger.debug("PatchRegisterNotice Controller Error", e);
            throw e;
            
        }

    }

    @PostMapping("/savemoney/registernotice")
    public ResponseEntity<?> PostRegisterNotice(@RequestBody Map<String, Object> noticeData) {
        
        try {
            
            BoardDTO boardDTO = new BoardDTO();

            String title = noticeData.get("title").toString();
            String content = noticeData.get("content").toString();
            String memberId = noticeData.get("memberId").toString();
            
            boardDTO.setTitle(title);
            boardDTO.setContent(content);
            boardDTO.setMemberId(memberId);

            boardService.registerNotice(boardDTO);


            return ResponseEntity.status(HttpStatus.OK).body(0);

        } catch (Exception e) {
            
            logger.debug("PostRegisterNotice Controller Error", e);
            throw e;
            
        }

    }

    @GetMapping("/savemoney/gotodetail")
    public ResponseEntity<?> getGoToDetail(@RequestParam String prors, @RequestParam String memberId) {
        
        try {
            
            int boardId = Integer.parseInt(prors);

            BoardDTO boardDTO = boardService.detailNotice(boardId);
            
            //로그인 사용자와 글 작성자 아이디가 같을 경우
            if(!boardDTO.getMemberId().equals(memberId)){
                boardService.increaseCount(boardId);
            }

            return ResponseEntity.status(HttpStatus.OK).body(boardDTO);

        } catch (Exception e) {
            
            logger.debug("getGoToDetail Controller Error", e);
            throw e;
            
        }

    }

    @GetMapping("/savemoney/mynotice/{memberId}/{currentPage}")
    public ResponseEntity<?> getMyNotice(@PathVariable String memberId, @PathVariable int currentPage) {
        
        try {
            
            Map<String, Object> boardListPage = new HashMap<>();
            List<BoardDTO> boardList = new ArrayList<>();

            //페이지 하나 당 개수
            int size = 10;

            int totalPage = boardService.totalBoardList(memberId);
            int firstSize = (currentPage - 1) * size + 1;
            int lastSize = currentPage * size;

            boardListPage.put("totalPage", totalPage);
            boardListPage.put("firstSize", firstSize);
            boardListPage.put("lastSize", lastSize);

            boardList = boardService.boardList(firstSize, lastSize, memberId);
            boardListPage.put("boardList", boardList);

            return ResponseEntity.status(HttpStatus.OK).body(boardListPage);

        } catch (Exception e) {
            
            logger.debug("getMyNotice Controller Error", e);
            throw e;
            
        }

    }

}
