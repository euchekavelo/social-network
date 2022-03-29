
package ru.skillbox.socnetwork.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtCsrfFilter extends GenericFilterBean {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            if (!tokenProvider.validateJwtToken(token)) {
                return;
            }
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(tokenProvider.getEmailFromToken(token));
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()));
        }
        chain.doFilter(request, response);
    }
}
