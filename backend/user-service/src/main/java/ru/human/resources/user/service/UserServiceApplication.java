package ru.human.resources.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Anton Kravchenkov
 * @since 14.07.2021
 */
@SpringBootApplication
@ComponentScan({
        "ru.human.resources.user.service.controller",
        "ru.human.resources.user.service.security",
        "ru.human.resources.common",
        "ru.human.resources.dao"
})
@EntityScan("ru.human.resources.dao")
@EnableJpaRepositories("ru.human.resources.dao.sql")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
