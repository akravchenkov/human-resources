package ru.human.resources.user.service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.human.resources.common.dao.api.UserService;
import ru.human.resources.common.data.User;
import ru.human.resources.user.service.service.security.auth.JwtAuthenticationToken;
import ru.human.resources.user.service.service.security.auth.jwt.extractor.TokenExtractor;
import ru.human.resources.user.service.service.security.model.token.RawAccessJwtToken;

/**
 * @author Anton Kravchenkov
 * @since 08.08.2021
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;

    @Autowired
    private TokenExtractor tokenExtractor;

    public AuthenticationFilter(
        UserService userService,
        Environment environment,
        AuthenticationManager authenticationManager
    ) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extractor(request));
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
//        try {
//            LoginRequest creds = new ObjectMapper()
//                .readValue(request.getInputStream(), LoginRequest.class);
//
//            return getAuthenticationManager().authenticate(
//                new UsernamePasswordAuthenticationToken(
//                    creds.getEmail(),
//                    creds.getPassword(),
//                    new ArrayList<>()
//                )
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) throws IOException, ServletException {
        String userName = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
        User userDetails = userService.findUserByEmail(userName);
        String token = Jwts.builder()
            .setSubject(userDetails.getUserId())
            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(
                Objects.requireNonNull(environment.getProperty("token.expiration_time")))))
            .signWith(SignatureAlgorithm.HS256, environment.getProperty("token.secret"))
            .compact();
        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}