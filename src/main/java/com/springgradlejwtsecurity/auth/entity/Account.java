package com.springgradlejwtsecurity.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String userId;

	private String password;

	private String permission;

	@Builder
	public Account(Long id, String userId, String password, String permission) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.permission = Optional.ofNullable(permission).orElse("ROLE_USER");
	}
}
