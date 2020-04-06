package com.example.usermanager.service.impl;

import com.example.usermanager.exceptions.TokenInvalidException;
import com.example.usermanager.security.SecurityConstants;
import com.example.usermanager.service.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.usermanager.security.SecurityConstants.*;
import static com.example.usermanager.security.SecurityConstants.TOKEN_AUDIENCE;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenServiceImpl.class);

    @Override
    public String generateToken(String login) {
        var signingKey = SecurityConstants.JWT_SECRET.getBytes();
        var token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", TOKEN_TYPE)
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_LIFETIME))
                //.claim("rol", roles)
                .compact();

        log.info("New JWT was generated");

        return token;
    }

    @Override
    public Jws<Claims> parseToken(String token) {
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            try {
                var signingKey = JWT_SECRET.getBytes();
                return Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }
        throw new TokenInvalidException("Token = " + token + " is incorrect token");
    }
}
