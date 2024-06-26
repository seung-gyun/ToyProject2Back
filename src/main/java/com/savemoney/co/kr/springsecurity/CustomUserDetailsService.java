package com.savemoney.co.kr.springsecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.savemoney.co.kr.mapper.MemberMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    public CustomUserDetailsService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        CustomUserDetail customUserDetail = memberMapper.findUserDetail(username);
        if (customUserDetail == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return customUserDetail;
    }
}