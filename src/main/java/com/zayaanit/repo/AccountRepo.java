package com.zayaanit.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zayaanit.entity.Account;
import com.zayaanit.entity.User;

/**
 * Zubayer Ahamed
 * @since May 9, 2026
 */
@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

	Optional<Account> findByNameAndUser(String name, User user);

	Page<Account> findAllByUser(User user, Pageable pageable);
}
