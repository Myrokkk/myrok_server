package com.example.myrok.controller;

import com.example.myrok.service.OAuth2Service;
import com.example.myrok.type.LoginProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myrok/auth")
public class AuthController {


    private final OAuth2Service oAuth2Service;


    //callback url 정보 담아오기
    @GetMapping("/google")
    public Map<String, String> getGoogleRedirectUrl(){
        Map<String, String> map = new HashMap<>();
        map.put("url", oAuth2Service.getRedirectUrl(LoginProvider.GOOGLE));
        return map;
    }

    @GetMapping("/callback")
    public void getGoogleAccessToken(String code, String scope, HttpServletResponse response) throws IOException {
        String accessToken = oAuth2Service.getAccessToken(code, LoginProvider.GOOGLE);
        oAuth2Service.login(response, accessToken, LoginProvider.GOOGLE);
    }

}