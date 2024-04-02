package com.figure.core.security.filters;

import com.figure.core.constant.HttpStatusConstant;
import com.figure.core.util.ResponseJsonUtil;
import com.figure.core.exception.error.ApiError;
import com.figure.core.security.MyUserDetailsService;
import com.figure.core.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws  IOException, ServletException {

        System.out.println("认证：Authorization......"+request.getRequestURL());

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String password = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("figure ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            }catch(ExpiredJwtException e){
                e.printStackTrace();
                ApiError apiError = new ApiError(HttpStatusConstant.AUTH_EXPIRED_CODE,HttpStatusConstant.AUTH_EXPIRED,e);
                ResponseJsonUtil.renderJson(response,apiError);
                return;
            }catch(SignatureException s){
                s.printStackTrace();
                ApiError apiError = new ApiError(HttpStatusConstant.SIGNATURE_ERROR_CODE,HttpStatusConstant.SIGNATURE_ERROR,s);
                ResponseJsonUtil.renderJson(response,apiError);
                return;
            }

        }

        System.out.println("Authorization......" + jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        request.setAttribute("jwt",jwt);
        chain.doFilter(request, response);
    }

}
