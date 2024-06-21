package com.savmoney.co.kr.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savmoney.co.kr.Service.MemberService;
import com.savmoney.co.kr.dto.MemberDTO;
import com.savmoney.co.kr.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberMapper memberMapper;

    // 멤버 insert
    public void joinMember(MemberDTO memberDTO){

        memberMapper.joinMember(memberDTO);

        /* 구현부 설정 */

    }

}
