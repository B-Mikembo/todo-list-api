package com.github.brice.todolistapi.adapter.out.persistence;

import com.github.brice.todolistapi.adapter.out.persistence.entity.TokenEntity;
import com.github.brice.todolistapi.application.out.Tokens;
import com.github.brice.todolistapi.application.token.Token;
import com.github.brice.todolistapi.application.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class TokensJpaAdapter implements Tokens {
    private static final String BEARER = "bearer";
    private final String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
    private final UsersJpaAdapter usersJpaAdapter;
    private final TokenJpaRepository tokenJpaRepository;

    public TokensJpaAdapter(UsersJpaAdapter usersJpaAdapter, TokenJpaRepository tokenJpaRepository) {
        this.usersJpaAdapter = usersJpaAdapter;
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public Token generate(User user) {
        var userEntity = usersJpaAdapter.loadUserByUsername(user.email());
        var tokenMap = generateJwt(userEntity);
        var token = new TokenEntity()
                .setKeyValue(tokenMap.get(BEARER))
                .setDisable(false)
                .setExpire(false)
                .setUserEntity(userEntity);
        tokenJpaRepository.save(token);
        return token.toDomain();
    }

    private Map<String, String> generateJwt(UserDetails userDetails) {
        var currentTime = System.currentTimeMillis();
        var expirationTime = currentTime + 30 * 60 * 1000;
        var claims = Map.of(
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, userDetails.getUsername()
        );

        var bearer = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .subject(userDetails.getUsername())
                .claims(claims)
                .signWith(getKey())
                .compact();

        return Map.of(BEARER, bearer);
    }

    private SecretKey getKey() {
        var decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    @Override
    public Token findByValue(String value) {
        return tokenJpaRepository.
                findByKeyValueAndDisableAndExpire(value, false, false)
                .orElseThrow(() -> new IllegalArgumentException("Unknown token"))
                .toDomain();
    }

    @Override
    public String findEmailInToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        var claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isTokenExpired(String token) {
        var expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
