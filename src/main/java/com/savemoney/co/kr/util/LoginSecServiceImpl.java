package com.savemoney.co.kr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LoginSecServiceImpl{

    private static final Logger logger = LoggerFactory.getLogger(LoginSecServiceImpl.class);

    public static String pwdEncryt(String memberPassword) throws NoSuchAlgorithmException{

        try {
         
            MessageDigest hashEncryt = MessageDigest.getInstance("SHA-256"); // SHA-256으로 암호화하겠다.
            hashEncryt.update(memberPassword.getBytes()); // memberPassword byte로 얻어와서 특정 Hash로 바꾸겠다.
            byte[] digest = hashEncryt.digest();

            String base64Hash = Base64.getEncoder().encodeToString(digest);

            return base64Hash;

        } catch (Exception e) {
            
            logger.error("login pwdEncryt Service Error",e);
            throw e;

        }

    }

}
