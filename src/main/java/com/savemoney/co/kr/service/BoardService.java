package com.savemoney.co.kr.service;

import java.util.List;

import com.savemoney.co.kr.dto.BoardDTO;


public interface BoardService {

    List<BoardDTO> boardList(int firstSize, int lastSize, String memberId);
    void updateNotice(BoardDTO boardDTO);
    void registerNotice(BoardDTO boardDTO);
    BoardDTO detailNotice(int boardId);
    void deleteNotice(Long boardId);
    void increaseCount(int boardId);
    int totalBoardList(String memberId);
} 