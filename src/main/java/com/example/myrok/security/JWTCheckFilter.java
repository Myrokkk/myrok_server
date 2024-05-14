package com.example.myrok.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    // jwt 토큰 없을 때
    // 필터링이 필요없기 때문
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        //true == not checking
        String path = request.getRequestURI();
        log.info("check uri--------------" + path);

        //부정의 부정
        // false == check
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("----------------------");
        log.info("---doFilterInternal---");
        log.info("----------------------");

        //test
        filterChain.doFilter(request, response);
    }
}
