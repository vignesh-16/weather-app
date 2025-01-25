package com.vignesh.weather.filter;

import com.vignesh.weather.services.JwtService;
import com.vignesh.weather.services.customUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    ApplicationContext context;

    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("::[JwtFilter]>> Filtering request for: " + request.getRequestURI());
        System.out.println("::[]>> with token: ");
        String requestURI = request.getRequestURI();

        // Skip filtering for specific endpoints
        if (requestURI.equals("/api/login") || requestURI.equals("/user/create")) {
            System.out.println("::[JwtFilter]>> Skipping validation for route - "+requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userid = null;

        System.out.println("::[]>> with token: "+authHeader);

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userid = jwtService.extractUserEmail(token);
        }

        if(userid != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            System.out.println("::[JwtFilter]>> It is calling the loadUserByUsername function !!");
            UserDetails userDetails = context.getBean(customUserDetailsService.class).loadUserByUsername(userid);
            if(jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
