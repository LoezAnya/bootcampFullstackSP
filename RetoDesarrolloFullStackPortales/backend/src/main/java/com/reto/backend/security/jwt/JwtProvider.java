package com.reto.backend.security.jwt;

import com.reto.backend.security.entity.IUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtProvider {
    private final static Logger logger= LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;
    

    public String generateToken(Authentication authentication){
        IUserDetails iUserDetails= (IUserDetails) authentication.getPrincipal();

        return Jwts.builder().setSubject(iUserDetails.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+expiration*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUserNameFromToken(String token){
        //return Jwts.parserBuilder().requireAudience(secret).build().parseClaimsJws(token).getBody().getAudience();
        //return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

    }

    public boolean validateToken(String token){

        try{
            // Jwts.parserBuilder().requireAudience(secret).build().parse(token);
            // return true;
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Malformed Token");
        }catch (UnsupportedJwtException e){
            logger.error("Unsupported Token");
        }catch (ExpiredJwtException e){
            logger.error("Expired Token");
        }catch (IllegalArgumentException e){
            logger.error("Empty Token" + e.getMessage());
        }


        return false;
    }
}
