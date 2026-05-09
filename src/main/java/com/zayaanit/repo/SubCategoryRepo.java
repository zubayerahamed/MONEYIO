package com.zayaanit.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zayaanit.entity.Category;
import com.zayaanit.entity.SubCategory;
import com.zayaanit.entity.User;

/**
 * Zubayer Ahamed
 * @since May 9, 2026
 */
@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {

	Optional<SubCategory> findByNameAndCategoryAndUser(String name, Category category, User user);

	Page<SubCategory> findAllByCategoryAndUser(Category category, User user, Pageable pageable);
}
