package com.savemoney.co.kr.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

import com.savemoney.co.kr.dto.MemberDTO;

@Mapper
public interface MemberMapper {

    public void joinMember(MemberDTO memberDTO) throws PersistenceException;
    public String findId(String email, String phoneNumber) throws PersistenceException;
    public String findDuple(String memberId) throws PersistenceException;
    public String findPwd(String memberId, String email) throws PersistenceException;
    public void resetPwd(String memberId, String memberPwd) throws PersistenceException;
    public String memberLogin(Map<String, String> params) throws PersistenceException;

}
