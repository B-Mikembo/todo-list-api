package com.github.brice.todolistapi.adapter.out.persistence.entity;

import com.github.brice.todolistapi.application.token.Token;
import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keyValue;
    private boolean disable;
    private boolean expire;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public boolean expire() {
        return expire;
    }

    public TokenEntity setExpire(boolean expire) {
        this.expire = expire;
        return this;
    }

    public Long id() {
        return id;
    }

    public TokenEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String value() {
        return keyValue;
    }

    public TokenEntity setKeyValue(String keyValue) {
        this.keyValue = keyValue;
        return this;
    }

    public boolean disable() {
        return disable;
    }

    public TokenEntity setDisable(boolean disable) {
        this.disable = disable;
        return this;
    }

    public UserEntity userEntity() {
        return userEntity;
    }

    public TokenEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public Token toDomain() {
        return new Token(keyValue, userEntity.toDomain());
    }
}
