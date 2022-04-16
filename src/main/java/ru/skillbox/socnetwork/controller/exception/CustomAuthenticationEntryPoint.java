package ru.skillbox.socnetwork.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String DEFAULT_ERROR_DESCRIPTION = "This request was denied. " +
            "Please register or use your existing account to get the updated token value and try again.";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(DEFAULT_ERROR_DESCRIPTION);
        response.setStatus(401);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, errorResponseDto);
        outputStream.flush();
    }
}
