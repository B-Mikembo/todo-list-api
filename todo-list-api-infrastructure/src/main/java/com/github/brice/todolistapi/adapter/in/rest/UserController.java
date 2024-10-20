package com.github.brice.todolistapi.adapter.in.rest;

import com.github.brice.todolistapi.adapter.in.rest.resource.LoginUserRequest;
import com.github.brice.todolistapi.adapter.in.rest.resource.RegisterUserRequest;
import com.github.brice.todolistapi.adapter.in.rest.resource.TokenResponse;
import com.github.brice.todolistapi.application.in.HandlingToken;
import com.github.brice.todolistapi.application.in.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final HandlingToken handlingToken;

    public UserController(AuthenticationManager authenticationManager, UserService userService, HandlingToken handlingToken) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.handlingToken = handlingToken;
    }

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered");
    }

    @PostMapping("/login")
    ResponseEntity<TokenResponse> login(@RequestBody LoginUserRequest request) {
        var authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        if (authenticate.isAuthenticated()) {
            return ResponseEntity.ok(TokenResponse.fromDomain(handlingToken.generateToken(request.toDomain())));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TokenResponse(null));
    }
}
