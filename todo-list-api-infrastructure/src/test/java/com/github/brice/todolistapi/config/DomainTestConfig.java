package com.github.brice.todolistapi.config;

import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.out.stub.InMemoryUsers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({DomainConfig.class, SecurityConfiguration.class, CryptedPasswordConfiguration.class})
public class DomainTestConfig {
    @Bean
    Users users() {
        return new InMemoryUsers();
    }
}
