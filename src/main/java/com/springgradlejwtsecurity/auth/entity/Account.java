package com.springgradlejwtsecurity.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;

	private String password;

	private Integer permission;

	@Builder
	public Account(Long id, String userId, String password, Integer permission) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.permission = permission;
	}
}
