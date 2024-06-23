package com.savemoney.co.kr.jwtUtil;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String secretKey = "abxasdasdasdsada12123aszsAAA!!!asdasd";

    public String getToken(String key, Object value){
        
        try {
            
            Date expTime = new Date();
            expTime.setTime(expTime.getTime()+ 1000*60*30);
            byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
            Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("typ", "JWT");
            headerMap.put("alg", "HS256");

            Map<String, Object> map = new HashMap<>();
            map.put(key, value);

            JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                                    .setClaims(map)
                                    .setExpiration(expTime)
                                    .signWith(SignatureAlgorithm.HS256, signKey);

        return builder.compact();

        } catch (Exception e) {

            logger.debug("jwtUtil Error", e);
            throw e;

        }
            

    }

    public Claims getClaims(String token){
        if(token!=null && !"".equals(token)){

            try {
                
                byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                Claims claims = Jwts.parser()
                                    .setSigningKey(signKey)
                                    .parseClaimsJws(token)
                                    .getBody();
                
                if(claims!=null)
                    return claims;
                

            } catch (ExpiredJwtException e) {
                
                logger.debug("jwtUtil ExpiredJwtException Error", e);
                throw e;

            } catch (JwtException e){
                
                logger.debug("jwtUtil JwtException Error", e);
                throw e;

            }

        }

        return null; //wingerms 2024.06.23 null여기 두는 것 확인

    }

    public boolean isValid(String token){

        try {
            
            return this.getClaims(token)!=null;

        } catch (Exception e) {
            logger.debug("jwtUtil isValid Error");
            throw e;
        }
            

    }

    public int getId(String token){

        try {
            
            Claims claims = this.getClaims(token);

            if(claims!=null)
                return Integer.parseInt(claims.get("id").toString());
            else{

                return 0; //wingerms 2024.06.23 0여기 두는 것 확인
                
            }

        } catch (Exception e) {
            
            logger.debug("jwtUtil getId Error");
            throw e;
        }



    }

}
