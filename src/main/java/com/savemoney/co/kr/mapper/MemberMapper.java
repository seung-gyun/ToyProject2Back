package com.savemoney.co.kr.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

import com.savemoney.co.kr.dto.MemberDTO;

@Mapper
public interface MemberMapper {

    public void joinMember(MemberDTO memberDTO) throws PersistenceException;
    public String findId(String memberId) throws PersistenceException;
    public String findPwd(String memberId, String email) throws PersistenceException;
    public String memberLogin(Map<String, String> params) throws PersistenceException;

}
