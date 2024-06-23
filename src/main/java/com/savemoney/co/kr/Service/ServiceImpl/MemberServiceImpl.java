package com.savemoney.co.kr.Service.ServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savemoney.co.kr.Securityutil.SecurityUtil;
import com.savemoney.co.kr.Service.MemberService;
import com.savemoney.co.kr.dto.MemberDTO;
import com.savemoney.co.kr.mapper.MemberMapper;

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
            String encryptPwd = SecurityUtil.pwdEncryt(memberDTO.getMemberPwd());
            memberDTO.setMemberPwd(encryptPwd);

            memberMapper.joinMember(memberDTO);

        } catch (Exception e) {
            
            logger.error("login joinMember Service Error",e);

        }

    }

    @Override
    public String findId(String  memberId){

        try {
        
            return memberMapper.findId(memberId);

        } catch (Exception e) {
            
            logger.error("login findId Service Error",e);
            throw e;
            
        }

    }

    @Override
    public String findPwd(String memberId, String email){


        try {
        
            return memberMapper.findPwd(memberId, email);

        } catch (Exception e) {
            
            logger.error("login findPwd Service Error",e);
            throw e;
            
        }
    }
    
    @Override
    public String memberLogin(Map<String, String> params){

        try {
            
            String pwd = params.get("password");

            //password를 암호화하는 수단 필요
            String encryptPwd = SecurityUtil.pwdEncryt(pwd);

            params.remove("password");
            params.put("memberPwd", encryptPwd);

            return memberMapper.memberLogin(params);

        // 알고리즘 암호화 실패시
        } catch (NoSuchAlgorithmException e) {
            logger.error("memberLogin Encryption algorithm not found", e);
            throw new RuntimeException("Encryption algorithm not found", e);
        }
        catch (Exception e) {
            
            logger.error("memberLogin Service Error",e);
            throw e;

        }

    }    

}
