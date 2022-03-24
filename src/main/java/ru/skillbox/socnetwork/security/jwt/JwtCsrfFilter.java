//package ru.skillbox.socnetwork.security.jwt;
//
//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.web.filter.GenericFilterBean;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class JwtCsrfFilter extends GenericFilterBean {
//
//    private final JwtTokenRepository tokenRepository;
//
//    private final HandlerExceptionResolver resolver;
//
//    public JwtCsrfFilter(JwtTokenRepository tokenRepository, HandlerExceptionResolver resolver) {
//        this.tokenRepository = tokenRepository;
//        this.resolver = resolver;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        request.setAttribute(ServletResponse.class.getName(), response);
//        CsrfToken csrfToken = this.tokenRepository.loadToken((HttpServletRequest) request);
//        boolean missingToken = csrfToken == null;
//        if (missingToken) {
//            csrfToken = this.tokenRepository.generateToken((HttpServletRequest) request);
//            this.tokenRepository.saveToken(csrfToken, (HttpServletRequest) request, (HttpServletResponse) response);
//        }
//        /*
//        request.setAttribute(CsrfToken.class.getName(), csrfToken);
//        request.setAttribute(csrfToken.getParameterName(), csrfToken);
//        if (request.getServletPath().equals("/auth/login")) {
//            try {
//                chain.doFilter(request, response);
//            } catch (Exception e) {
//                resolver.resolveException(request, response, null, new MissingCsrfTokenException(csrfToken.getToken()));
//            }
//        } else {
//            String actualToken = request.getHeader(csrfToken.getHeaderName());
//            if (actualToken == null) {
//                actualToken = request.getParameter(csrfToken.getParameterName());
//            }
//            try {
//                if (!StringUtils.isEmpty(actualToken)) {
//                    Jwts.parser()
//                            .setSigningKey(((JwtTokenRepository) tokenRepository).getSecret())
//                            .parseClaimsJws(actualToken);
//
//                    chain.doFilter(request, response);
//                } else
//                    resolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
//            } catch (JwtException e) {
//                if (this.logger.isDebugEnabled()) {
//                    this.logger.debug("Invalid CSRF token found for " + UrlUtils.buildFullRequestUrl(request));
//                }
//
//                if (missingToken) {
//                    resolver.resolveException(request, response, null, new MissingCsrfTokenException(actualToken));
//                } else {
//                    resolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
//                }
//            }
//        }*/
//
//        //String token = tokenRepository.generateToken((ServletRequest))
//
//        // Получаю параметр
//        String email = request.getParameter("username");
//        System.out.println(email);
//
//        // Просто вывожу в консоль
//
//        chain.doFilter(request, response);
//    }
//}