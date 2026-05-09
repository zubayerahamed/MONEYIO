package com.zayaanit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zayaanit.entity.Transaction;

/**
 * Zubayer Ahamed
 * @since May 9, 2026
 */
@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
