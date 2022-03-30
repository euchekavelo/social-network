package ru.skillbox.socnetwork.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.MimeTypeUtils;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(new GeneralResponse<>(
                "Error.UNAUTHORIZED.getErrorName()",
                e.getMessage(),
                httpServletRequest.getPathInfo(),
                System.currentTimeMillis()
        )));
        httpServletResponse.getWriter().flush();
        log.info("IN handle - handled AccessDeniedException {}", e.getMessage());
    }


}