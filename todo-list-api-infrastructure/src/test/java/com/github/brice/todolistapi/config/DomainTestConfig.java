package com.github.brice.todolistapi.config;

import com.github.brice.todolistapi.adapter.out.persistence.UserJpaAdapter;
import com.github.brice.todolistapi.adapter.out.persistence.UserJpaRepository;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.out.stub.InMemoryUsers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
@Import({DomainConfig.class, SecurityConfiguration.class, CryptedPasswordConfiguration.class})
public class DomainTestConfig {
    @Bean
    Users users() {
        return new InMemoryUsers();
    }
}
