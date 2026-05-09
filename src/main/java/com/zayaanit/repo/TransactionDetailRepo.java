package com.zayaanit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zayaanit.entity.Transaction;
import com.zayaanit.entity.TransactionDetail;

/**
 * Zubayer Ahamed
 * @since May 9, 2026
 */
@Repository
public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, Long> {

	List<TransactionDetail> findAllByTransaction(Transaction transaction);
}
