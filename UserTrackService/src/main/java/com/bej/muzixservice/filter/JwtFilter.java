package com.bej.muzixservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request,response);
        }
        else if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            throw new ServletException("Missing or Invalid Token");
        }

        String token = authHeader.substring(7);//Bearer => 6+1 = 7, since token begins with Bearer
        Claims claims = Jwts.parser().setSigningKey("privateKey").parseClaimsJws(token).getBody();
        request.setAttribute("claims",claims);
        filterChain.doFilter(request,response);
    }
}


