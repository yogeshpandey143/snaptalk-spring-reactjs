package com.example.snaptalk.SnapTalk.configsecutiry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public  class JwtProvider {
//    private static SecretKey key= Keys.hmacShaKeyFor((JwtContant.SECRET_KEY.getBytes()));

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // securely generates a key

    // rest of your class implementation
     public static String generateToken(Authentication auth){
      String jwt = Jwts.builder().setIssuer("SnapTalk")
              .setIssuedAt(new Date())
              .setExpiration(new Date(new Date().getTime()+86400000))
              .claim("email",auth.getName()).signWith(key).compact();


      return jwt;
  }

  public  static String getEmailFromJwtToken(String jwt){
     jwt = jwt.substring(7);
      Claims claims= Jwts.parserBuilder().setSigningKey(key).build()
              .parseClaimsJws(jwt).getBody();


      String email = String.valueOf(claims.get("email"));
     return email;
    }
}
