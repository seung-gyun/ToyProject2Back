package com.savemoney.co.kr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

import com.savemoney.co.kr.dto.BoardDTO;

@Mapper
public interface BoardMapper {

   List<BoardDTO> boardList(int firstSize, int lastSize) throws PersistenceException;
   void registerNotice(BoardDTO boardDTO) throws PersistenceException;
   void updateNotice(BoardDTO boardDTO) throws PersistenceException;
   void deleteNotice(Long boardId) throws PersistenceException;
   BoardDTO detailNotice(int boardId) throws PersistenceException;
   void increaseCount(int boardId) throws PersistenceException;
   int totalBoardList() throws PersistenceException ;
   
}
