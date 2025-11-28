package com.self.SpringJDBCdemo.security;

//A JSON Web Token (JWT) is a compact, URL-safe means of representing claims (information) to be transferred between two parties.
//A JWT is a string divided into three parts, separated by dots (.)
//Header (JOS Header) //Payload (Claims)  //Signature
//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9 . eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ . SflKxwRJSMeKKF2QT4fWsyDjmAVhT5TzSdGWfNawERk
//{"alg": "HS256", "typ": "JWT"}        //{"sub": "1234567890", "name": "John Doe", "iat": 1516239022}
//alg = algorithm used  JWT = Authentication type  sub = (Subject):User ID  name: User's name. iat (Issued At): Timestamp when the token was issued.
//The signature ensures the token's integrity. It is generated using the specified algorithm (HS256) over the concatenated (Header + Payload) string, along with a secret key known only to the server.

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "nZr7P4lv+Y3rW7DH8sq8J8pQHXRuubIG9JpncCEgh9U=";


    public String generateToken(String username){
        return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis()+32434432)).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
