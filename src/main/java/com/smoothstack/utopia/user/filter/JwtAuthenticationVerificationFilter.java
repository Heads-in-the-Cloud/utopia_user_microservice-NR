package com.smoothstack.utopia.user.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class JwtAuthenticationVerificationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationVerificationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {
            String header = request.getHeader(AUTHORIZATION);
            if (header != null && header.startsWith("Bearer ")) {
                UsernamePasswordAuthenticationToken authToken = getAuthenticationToken(request);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.debug("Auth success.");
            }
            chain.doFilter(request, response);
        } catch (TokenExpiredException ex) {
            log.debug("Expired token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"token expired\"}");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (token == null) {
            return null;
        }

        DecodedJWT jwt = JWT.require(Algorithm.HMAC256("secret"))
                .build()
                .verify(token.replace("Bearer ", ""));

        String subject = jwt.getSubject();

        if (subject == null) {
            return null;
        }

        List<SimpleGrantedAuthority> authorities = jwt.getClaim("roles")
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(subject, null, authorities);
    }
}

