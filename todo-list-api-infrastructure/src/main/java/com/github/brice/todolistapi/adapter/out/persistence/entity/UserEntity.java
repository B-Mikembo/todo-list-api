package com.github.brice.todolistapi.adapter.out.persistence.entity;

import com.github.brice.todolistapi.application.user.User;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {
    private final boolean active = true;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String name;
    private String email;

    public UserEntity(Long id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserEntity() {
    }

    public static UserEntity fromDomain(User user) {
        var userEntity = new UserEntity();
        userEntity.setId(user.id());
        userEntity.setEmail(user.email());
        userEntity.setName(user.name());
        userEntity.setPassword(user.password());
        return userEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean active() {
        return active;
    }

    public Long id() {
        return id;
    }

    public String password() {
        return password;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public User toDomain() {
        return new User(
                id,
                password,
                name,
                email
        );
    }
}
