package com.zayaanit.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zayaanit.entity.Category;
import com.zayaanit.entity.User;

/**
 * Zubayer Ahamed
 * May 8, 2026
 */
@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	Optional<Category> findByNameAndUser(String name, User user);

	Page<Category> findAllByUser(User user, Pageable pageable);
}
