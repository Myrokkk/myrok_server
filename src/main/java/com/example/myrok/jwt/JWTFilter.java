//package com.example.myrok.jwt;
//
//import com.example.myrok.domain.UserEntity;
//import com.example.myrok.security.CustomUserDetails;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Log4j2
//public class JWTFilter extends OncePerRequestFilter {
//
//    private final JWTUtil jwtUtil;
//
//    public JWTFilter(JWTUtil jwtUtil) {
//
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String requestUri = request.getRequestURI();
//
//        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {
//
//            filterChain.doFilter(request, response);
//            return;
//        }
//        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
//
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 헤더에서 access키에 담긴 토큰을 꺼냄
//        String accessToken = request.getHeader("access");
//
//// 토큰이 없다면 다음 필터로 넘김
//        if (accessToken == null) {
//
//            filterChain.doFilter(request, response);
//
//            return;
//        }
//
//// 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
//        try {
//            jwtUtil.isExpired(accessToken);
//        } catch (ExpiredJwtException e) {
//
//            //response body
//            PrintWriter writer = response.getWriter();
//            writer.print("access token expired");
//
//            //response status code
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//// 토큰이 access인지 확인 (발급시 페이로드에 명시)
//        String category = jwtUtil.getCategory(accessToken);
//
//        if (!category.equals("access")) {
//
//            //response body
//            PrintWriter writer = response.getWriter();
//            writer.print("invalid access token");
//
//            //response status code
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//// username, role 값을 획득
//        String username = jwtUtil.getUsername(accessToken);
//        String role = jwtUtil.getRole(accessToken);
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername(username);
//        userEntity.setRole(role);
//        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
//
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        filterChain.doFilter(request, response);
//    }
//
//
//}