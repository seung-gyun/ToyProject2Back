package com.savemoney.co.kr.service;


import java.util.Map;

import com.savemoney.co.kr.dto.MemberDTO;


public interface MemberService {

    void joinMember(MemberDTO memberDTO);
    String findId(String email, String phoneNumber);
    String findDuple(String memberId);
    String findPwd(String memberId, String email);
    String memberLogin(Map<String, String> params);
    void updateMember(MemberDTO memberDTO);
    void deleteMember(String memberId);

}
