package com.example.myrok.security;

import com.example.myrok.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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

        String authHeaderStr = request.getHeader("Authorization");

        //앞이 Bearer+공백까지 7글자이고 그 다음부터가 JWT 문자열
        //앞에 7개를 잘라내기
        try {
            //Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            filterChain.doFilter(request, response);

        }catch(Exception e){

            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();

        }
    }
}
