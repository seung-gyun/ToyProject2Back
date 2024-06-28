package com.savemoney.co.kr.service.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savemoney.co.kr.controller.BoardController;
import com.savemoney.co.kr.dto.BoardDTO;
import com.savemoney.co.kr.mapper.BoardMapper;
import com.savemoney.co.kr.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    BoardMapper boardMapper;

    @Override
    public List<BoardDTO> boardList() {
        try {
            
            return boardMapper.boardList();

        } catch (Exception e) {

            logger.debug("boardList BoardServiceImpl Error ",e);
            throw e;

        }
    }

}
