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
    public List<BoardDTO> boardList(int firstSize, int lastSize, String memberId) {
        try {
            
            return boardMapper.boardList(firstSize, lastSize, memberId);

        } catch (Exception e) {

            logger.debug("boardList BoardServiceImpl Error ",e);
            throw e;

        }
    }

    @Override
    public void registerNotice(BoardDTO boardDTO){
        try {
            
            boardMapper.registerNotice(boardDTO);

        } catch (Exception e) {
            
            logger.debug("registerNotice BoardServiceImpl Error ",e);
            throw e;

        }
    };

    @Override
    public void updateNotice(BoardDTO boardDTO){
        try {
            
            boardMapper.updateNotice(boardDTO);

        } catch (Exception e) {
            
            logger.debug("registerNotice BoardServiceImpl Error ",e);
            throw e;

        }
    };

    @Override
    public void deleteNotice(Long boardId){
        try {
            
            boardMapper.deleteNotice(boardId);

        } catch (Exception e) {
            
            logger.debug("deleteNotice BoardServiceImpl Error ",e);
            throw e;

        }
    };

    @Override
    public BoardDTO detailNotice(int boardId){
        try {
            
            BoardDTO boardDto = boardMapper.detailNotice(boardId);

            return boardDto;

        } catch (Exception e) {
            
            logger.debug("detailNotice BoardServiceImpl Error ",e);
            throw e;

        }
    };


    
    @Override
    public void increaseCount(int boardId){

        try {
            
            boardMapper.increaseCount(boardId);

        } catch (Exception e) {
            
            logger.debug("increaseCount BoardServiceImpl Error ",e);
            throw e;

        }

    }
    
    @Override
    public int totalBoardList(String memberId){

        try {
            
            return boardMapper.totalBoardList(memberId);

        } catch (Exception e) {
            
            logger.debug("increaseCount BoardServiceImpl Error ",e);
            throw e;

        }

    }
}


