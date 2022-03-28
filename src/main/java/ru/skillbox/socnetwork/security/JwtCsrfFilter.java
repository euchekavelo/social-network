
package ru.skillbox.socnetwork.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                HttpServletResponse servletResponse = (HttpServletResponse) response;
                new SecurityContextLogoutHandler().setClearAuthentication(true);
                servletResponse.setHeader("Authorization", null);
//                servletResponse.
//                servletResponse.setStatus(403);
//                servletResponse.
//                servletResponse.sendRedirect("/api/v1/auth/logout");
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
