package com.zayaanit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zayaanit.entity.User;

/**
 * Zubayer Ahamed
 * Apr 22, 2026
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
