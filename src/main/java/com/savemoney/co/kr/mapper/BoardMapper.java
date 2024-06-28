package com.savemoney.co.kr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

import com.savemoney.co.kr.dto.BoardDTO;

@Mapper
public interface BoardMapper {

   List<BoardDTO> boardList() throws PersistenceException;

}
