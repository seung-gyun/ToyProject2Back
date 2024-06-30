package com.savemoney.co.kr.service;

import java.util.List;


import com.savemoney.co.kr.dto.BoardDTO;


public interface BoardService {

    List<BoardDTO> boardList(int firstSize, int lastSize);
    void registerNotice(BoardDTO boardDTO);
    BoardDTO detailNotice(int boardId);
    void increaseCount(int boardId);
    int totalBoardList();
} 