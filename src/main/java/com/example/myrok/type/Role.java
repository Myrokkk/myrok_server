package com.example.myrok.type;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("작성자"),
    PARTICIPANT("참여자"),
    TEAMMEMBER("팀원");

    private String value;

    Role(String status) {
        this.value = status;
    }
}