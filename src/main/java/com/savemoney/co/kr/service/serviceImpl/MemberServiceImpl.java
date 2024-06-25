package com.savemoney.co.kr.service.serviceImpl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savemoney.co.kr.decencutil.SecurityUtil;
import com.savemoney.co.kr.dto.MemberDTO;
import com.savemoney.co.kr.mapper.MemberMapper;
import com.savemoney.co.kr.service.MemberService;

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
    public String findId(String email, String phoneNumber){

        try {
        
            return memberMapper.findId(email, phoneNumber);
            

        } catch (Exception e) {
            
            logger.error("login findId Service Error",e);
            throw e;
            
        }

    }

    @Override
    public String findDuple(String memberId){

        try {
        
            return memberMapper.findDuple(memberId);

        } catch (Exception e) {
            
            logger.error("login findId duplicate Service Error",e);
            throw e;
            
        }

    }

    @Override
    public String findPwd(String memberId, String email){


        try {
        
             char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
            'y', 'z','!', '@', '#', '$', '%', '^', '&' };
            
            String findPwd = memberMapper.findPwd(memberId, email);
            String memberPwd = "";

            if(findPwd!=null){

                StringBuffer sb = new StringBuffer();        
                SecureRandom sr = new SecureRandom();        
                sr.setSeed(new Date().getTime());  

                int idx = 0;        
                int len = charSet.length;        
                for (int i=0; i<10; i++) {         

                    idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.            
                    sb.append(charSet[idx]);

                }

                memberPwd = sb.toString();
                memberMapper.resetPwd(memberId, SecurityUtil.pwdEncryt(memberPwd));

                return memberPwd;

            }else{

                //null
                return memberPwd;

            }

        }
        catch (NoSuchAlgorithmException e) {
            logger.error("findPwd Encryption algorithm not found", e);
            throw new RuntimeException("Encryption algorithm not found", e);
        }
        catch (Exception e) {
            
            logger.error("findPwd Service Error",e);
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
