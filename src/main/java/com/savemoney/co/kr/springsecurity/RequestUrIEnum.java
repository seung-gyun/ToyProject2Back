package com.savemoney.co.kr.springsecurity;

enum RequestUrIEnum {

    BOARD("/savemoney/board/**"),
    REGISTER_NOTICE("/savemoney/registernotice/**"),
    MY_NOTICE("/savemoney/mynotice/**"),
    MY_PAGE("/savemoney/mypage/**"),
    GO_TO_DETAIL("/savemoney/gotodetail/**"),
    UPDATE_NOTICE("/savemoney/updatenotice/**"),
    DELETE_NOTICE("/savemoney/deletenotice/**");

    private final String pattern;

    RequestUrIEnum(String pattern) {  // 생성자명 수정
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }


}
