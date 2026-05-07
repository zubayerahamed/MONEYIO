package com.zayaanit.entity;

import java.util.ArrayList;
import java.util.List;

import com.zayaanit.enums.CategoryType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Zubayer Ahamed Apr 23, 2026
 */
@Data
@Entity
@Table(name = "categories")
@EqualsAndHashCode(callSuper = true)
public class Category extends AbstractModel<Long> {

	private static final long serialVersionUID = -7934056111745106131L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 10, nullable = false)
	private CategoryType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TransactionDetail> transactionDetails = new ArrayList<>();
}
