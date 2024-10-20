package com.github.brice.todolistapi.adapter.in.rest;

import com.github.brice.todolistapi.adapter.in.rest.resource.RegisterUserRequest;
import com.github.brice.todolistapi.application.in.UserService;
import com.github.brice.todolistapi.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered");
    }
}
