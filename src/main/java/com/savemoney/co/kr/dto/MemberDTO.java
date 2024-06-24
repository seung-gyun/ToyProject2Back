package com.savemoney.co.kr.dto;

public class MemberDTO {

    private String memberId;
    private String memberPwd;
    private String phoneNumber;
    private String email;
    private String address;

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }    

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getMemberPwd() {
        return memberPwd;
    }    

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }    

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }    

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    } 

}
