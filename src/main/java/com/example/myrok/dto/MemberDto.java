package com.example.myrok.dto;

import com.example.myrok.domain.MemberProject;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDto extends User {

    private Long id;
    private String name;
    private String socialId;
    private String password;
    private boolean deleted;
    private String imgUrl;
    //화면쪽에서 처리하기 쉽게
    //MemberProject타입이 아닌 String타입으로 지정
    private List<String> memberProjects;

    public MemberDto(Long id, String name, String socialId, String password, boolean deleted, String imgUrl, List<String> memberProjects) {
        super(
                name,
                password,
                memberProjects.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList()));

        this.id = id;
        this.name = name;
        this.socialId = socialId;
        this.password = password;
        this.deleted = deleted;
        this.imgUrl = imgUrl;
        this.memberProjects = memberProjects;
        }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", id);
        dataMap.put("name", name);
        dataMap.put("socialId", socialId);
        dataMap.put("password", password);
        dataMap.put("deleted", deleted);
        dataMap.put("imgUrl", imgUrl);
        dataMap.put("memberProjects", memberProjects);

        return dataMap;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @ToString
    public static class MemberNameDto{
        private Long memberId;
        private String name;
    }
}
