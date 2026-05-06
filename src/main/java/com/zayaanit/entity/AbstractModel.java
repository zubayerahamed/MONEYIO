package com.zayaanit.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

/**
 * Zubayer Ahamed
 * Apr 22, 2026
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModel<U> implements Serializable {

	private static final long serialVersionUID = -4906574398205703383L;

	@CreatedBy
	@Column(name = "created_by", length = 25, updatable = false)
	private U created_by;

	@LastModifiedBy
	@Column(name = "updated_by", length = 25, nullable = true)
	private U updatedBy;

	@CreationTimestamp
	@Column(name = "created", nullable = false, updatable = false)
	private LocalDateTime created;

	@UpdateTimestamp
	@Column(name = "updated", nullable = true)
	private LocalDateTime updated;

}
