package socialnetwork.backend.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import socialnetwork.backend.dto.request.LoginDto;
import socialnetwork.backend.dto.request.ResponseDto;
import socialnetwork.backend.dto.request.UnsuccessfulLogin;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.model.admin.Admin;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;
import socialnetwork.backend.repository.admin.AdminRepository;
import socialnetwork.backend.repository.visitorProfile.VisitorProfileRepository;
import socialnetwork.backend.repository.visitor.VisitorRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import com.auth0.jwt.JWT;

import static socialnetwork.backend.security.SecurityConstants.*;

@Slf4j
public class JWTAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final VisitorRepository visitorRepository;
    private final VisitorProfileRepository visitorProfileRepository;
    private final AdminRepository adminRepository;

    LoginDto credential = new LoginDto();

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        this.authenticationManager = authenticationManager;
        visitorProfileRepository = context.getBean(VisitorProfileRepository.class);
        visitorRepository = context.getBean(VisitorRepository.class);
        adminRepository = context.getBean(AdminRepository.class);
        setFilterProcessesUrl("/user/login");
    }


    @Override
    @ExceptionHandler(GeneralException.class)
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            credential = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credential.getEmail(),
                            credential.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException exception) {
            throw new RuntimeException("Uuh.. Sorry, visitor does not exist.");
        }
    }

    @Override
    @ExceptionHandler(GeneralException.class)
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes(StandardCharsets.UTF_8)));

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDto responseDto;
        String email = ((User) authResult.getPrincipal()).getUsername();
        VisitorProfile user = visitorProfileRepository.findByEmail(email);

        if (credential.getType() != null && credential.getType().equals("ADMIN")) {
            Admin admin = adminRepository.findByUser(user);

            if (admin == null) {
                throw new javax.security.sasl.AuthenticationException("Admin user details can't be null.");
            }
            responseDto = new ResponseDto();

            responseDto.setId(admin.getId());
            responseDto.setCreatedDate(admin.getCreatedDate().toString());
            responseDto.setFirstName(user.getFirstName());
            responseDto.setLastName(user.getLastName());
            responseDto.setIsActive(admin.getIsActive());
            responseDto.setIsVerified(user.getIsVerified());
            responseDto.setPhoneNumber(user.getEmail());
            responseDto.setToken(token);
        }

        else {
            Visitor visitor = visitorRepository.findByUser(user);
            responseDto = new ResponseDto();

            responseDto.setEmail(user.getEmail());
            responseDto.setCreatedDate(LocalDateTime.now().toString());
            responseDto.setFirstName(user.getFirstName());
            responseDto.setId(visitor.getId());
            responseDto.setToken(visitor.getVerificationToken());
            responseDto.setModifiedDate(LocalDateTime.now().toString());
            responseDto.setLastName(user.getLastName());
            responseDto.setIsActive(visitor.getIsActive());
            responseDto.setIsVerified(user.getIsVerified());
            responseDto.setModifiedDate(LocalDateTime.now().toString());
            responseDto.setPhoneNumber(visitor.getPhoneNumber());
            responseDto.setSex(visitor.getGender());
            responseDto.setToken(token);

        }

        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.getOutputStream().print("{ \"data\":" + objectMapper.writeValueAsString(responseDto) + "}");
        response.flushBuffer();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        UnsuccessfulLogin responseDetails = new UnsuccessfulLogin(LocalDateTime.now(), "Login error. Incorrect email or password.", "Bad request", "/user/login");
        response.getOutputStream().print("{ \"message\":" + responseDetails + "}");
    }

}
