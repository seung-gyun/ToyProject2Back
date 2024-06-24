package com.savemoney.co.kr.service;


import java.util.Map;

import com.savemoney.co.kr.dto.MemberDTO;


public interface MemberService {

    void joinMember(MemberDTO memberDTO);
    String findId(String memberId);
    String findPwd(String memberId, String email);
    String memberLogin(Map<String, String> params);

}
