package com.savmoney.co.kr.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.savmoney.co.kr.dto.MemberDTO;

@Mapper
public interface MemberMapper {

    void joinMember(MemberDTO memberDTO);

}
