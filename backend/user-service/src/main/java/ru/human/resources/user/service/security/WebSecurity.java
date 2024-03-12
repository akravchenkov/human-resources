package ru.human.resources.user.service.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.human.resources.common.dao.api.user.UserService;
import ru.human.resources.user.service.security.auth.RestAuthenticationProvider;

/**
 * @author Anton Kravchenkov
 * @since 07.08.2021
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor_ = @Autowired)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final Environment environment;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //    private final RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider;
    private final RestAuthenticationProvider restAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/vi/user/login").permitAll()
            .antMatchers("/api/vi/users/**").permitAll()
//            .hasIpAddress(environment.getProperty("user.service.ip"))
            .and()
            .addFilter(getApplicationFilter());
        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getApplicationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,
            environment, authenticationManager());
//        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/api/v1/user/login");
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(restAuthenticationProvider);
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
