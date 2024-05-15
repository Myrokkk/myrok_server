package com.example.myrok.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {


        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }





//    //간단하게 테스트하기 위한 key
//    private static String key = "1234567890123456789012345678901234567890";
//
//    public static String generateToken(Map<String, Object> valueMap, int min) {
//
//        SecretKey key = null;
//
//        try{
//            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
//
//        }catch(Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//
//        String jwtStr = Jwts.builder()
//                .setHeader(Map.of("typ","JWT"))
//                .setClaims(valueMap)
//                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
//                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
//                .signWith(key)
//                .compact();
//
//        return jwtStr;
//    }
//
//    public static Map<String, Object> validateToken(String token) {
//
//        Map<String, Object> claim = null;
//
//        try{
//
//            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
//
////            claim = Jwts.parserBuilder()
////                    .setSigningKey(key)
////                    .build()
////                    .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
////                    .getBody();
//
//        }catch(MalformedJwtException malformedJwtException){
//            throw new CustomJWTException("MalFormed");
//        }catch(ExpiredJwtException expiredJwtException){
//            throw new CustomJWTException("Expired");
//        }catch(InvalidClaimException invalidClaimException){
//            throw new CustomJWTException("Invalid");
//        }catch(JwtException jwtException){
//            throw new CustomJWTException("JWTError");
//        }catch(Exception e){
//            throw new CustomJWTException("Error");
//        }
//        return claim;
//    }


}
