package com.zayaanit.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Zubayer Ahamed
 * May 7, 2026
 */
@Data
@Entity
@Table(name = "transaction_details")
@EqualsAndHashCode(callSuper = true)
public class TransactionDetail extends AbstractModel<Long> {

	private static final long serialVersionUID = -2567203039721493737L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", nullable = false)
	private Transaction transaction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "xsign", nullable = false)
	private int xsign;

	@Column(name = "charge", nullable = false)
	private boolean charge;
}
