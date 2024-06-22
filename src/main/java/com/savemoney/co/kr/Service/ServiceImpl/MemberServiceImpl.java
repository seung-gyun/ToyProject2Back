package com.savemoney.co.kr.Service.ServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savemoney.co.kr.Service.MemberService;
import com.savemoney.co.kr.dto.MemberDTO;
import com.savemoney.co.kr.mapper.MemberMapper;
import com.savemoney.co.kr.util.LoginSecServiceImpl;

@Service
public class MemberServiceImpl implements MemberService{

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    MemberMapper memberMapper;

    // 멤버 insert
    @Override
    public void joinMember(MemberDTO memberDTO){

        try {
            
            //password를 암호화하는 수단 필요
            String encryptPwd = LoginSecServiceImpl.pwdEncryt(memberDTO.getMemberPwd());
            memberDTO.setMemberPwd(encryptPwd);

            memberMapper.joinMember(memberDTO);

        } catch (Exception e) {
            
            logger.error("login joinMember Service Error",e);

        }

    }

    public String findId(String  memberId){

        try {
        
            return memberMapper.findId(memberId);

        } catch (Exception e) {
            
            logger.error("login findId Service Error",e);
            throw e;
            
        }



    }

    public String findPwd(String memberId, String email){


        try {
        
            return memberMapper.findPwd(memberId, email);

        } catch (Exception e) {
            
            logger.error("login findPwd Service Error",e);
            throw e;
            
        }
    }
    
    public String memberLogin(Map<String, String> params) throws NoSuchAlgorithmException{

        try {
            
            String pwd = params.get("password");
            String encryptPwd = LoginSecServiceImpl.pwdEncryt(pwd);

            params.remove("password");
            params.put("memberPwd", encryptPwd);

            //password를 암호화하는 수단 필요
            

            return memberMapper.memberLogin(params);

        } catch (Exception e) {
            
            logger.error("login joinMember Service Error",e);
            throw e;

        }

    }    

}
