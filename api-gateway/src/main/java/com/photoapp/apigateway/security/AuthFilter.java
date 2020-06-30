package com.photoapp.apigateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthFilter extends BasicAuthenticationFilter {

    Environment environment;

    public AuthFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;        
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain)
					throws IOException, ServletException {
        String authHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authHeader == null || !authHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken auth = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String authHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));
        
        if (authHeader == null) {
            return null;
        }

        String token = authHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");

        String userId = Jwts.parser()
            .setSigningKey(environment.getProperty("token.secret"))
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        
        if (userId == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }
    
    
}