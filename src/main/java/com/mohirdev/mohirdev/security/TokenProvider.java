package com.mohirdev.mohirdev.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component

public class TokenProvider {
    private  final Logger logger= LoggerFactory.getLogger(TokenProvider.class);
    private long tokenValidateMillisecondRemember;
    private static  final String AUTHKEY="auth";
    private long tokenValidateMillisecond;

    private  final Key key;
    private final JwtParser jwtParser;

    public TokenProvider(){
        byte[] keyByte;
        String secret="YnUgbWVuaW5nIHNlY3JldGltIA==";
        keyByte= Decoders.BASE64.decode(secret);
     //    key= Keys.hmacShaKeyFor(keyByte);
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        jwtParser=Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidateMillisecondRemember=1000*86400;
        this.tokenValidateMillisecond=1000*3600;
    }

    public String createToken(Authentication authentication, boolean remebmerMe) {
        String authorites=authentication.getAuthorities().stream().
                map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now=(new Date().getTime());
        Date validate;
        if(remebmerMe){
            validate=new Date(now+tokenValidateMillisecondRemember);
        }else {
            validate=new Date(now+tokenValidateMillisecond);
        }

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHKEY,authorites)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validate)
                .compact();

    }

    public boolean validateToken(String jwt) {
        try {
            jwtParser.parseClaimsJws(jwt);
            return true;
        }catch (ExpiredJwtException e){
            logger.error("ExpiredJwtException-->");
        }catch (UnsupportedJwtException e){
            logger.error("UnsupportedJwtException");
        }catch (MalformedJwtException e){
            logger.error("MalformedJwtException");
        }catch (SignatureException e){
            logger.error("SignatureException");
        }catch (IllegalArgumentException e){
            logger.error("IllegalArgumentException");
        }
        return  false;
    }

    public Authentication getAuthentication(String jwt) {
        Claims claims=jwtParser.parseClaimsJws(jwt).getBody();
        Collection<? extends GrantedAuthority> authories=Arrays
                .stream(claims.get(AUTHKEY).toString().split(","))
                .filter(auth->!auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User prinspal=new User(claims.getSubject(),"",  authories);
        return  new UsernamePasswordAuthenticationToken(prinspal,jwt,authories);
    }
}
