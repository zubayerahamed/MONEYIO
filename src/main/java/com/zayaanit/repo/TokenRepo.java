package com.zayaanit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zayaanit.entity.Token;

/**
 * Zubayer Ahamed Apr 22, 2026
 */
@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

	List<Token> findAllByUserIdAndRevokedAndExpired(Long userId, boolean revoked, boolean expired);

	Optional<Token> findByToken(String token);
}
