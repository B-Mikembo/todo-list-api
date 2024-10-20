package com.github.brice.todolistapi.adapter.out.persistence;

import com.github.brice.todolistapi.adapter.out.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByKeyValueAndDisableAndExpire(String keyValue, boolean disable, boolean expire);
}
