package com.github.brice.todolistapi.application;

import com.github.brice.todolistapi.application.in.UserService;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.out.stub.InMemoryUsers;
import com.github.brice.todolistapi.application.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AcceptanceTest {
    private Users users;
    private UserService userService;

    @BeforeEach
    void setup() {
        users = new InMemoryUsers();
        userService = new UserServiceImpl(users);
    }

    @Test
    void customerCanRegisterUser() {
        var userToRegister = new User("password", "name", "firstname.lastname@gmail.com");
        var user = userService.register(userToRegister);
        assertThat(user).isNotNull();
        assertThat(user.id()).isNotNull();
        assertThat(user.name()).isEqualTo("name");
        assertThat(user.password()).isEqualTo("password");
        assertThat(user.email()).isEqualTo("firstname.lastname@gmail.com");
    }

    @Test
    void customerCannotRegisterUserWithExistingEmail() {
        var userToRegister = new User("password", "name", "firstname.lastname@gmail.com");
        userService.register(userToRegister);
        assertThatThrownBy(() -> userService.register(userToRegister)).isInstanceOf(RuntimeException.class);
    }
}