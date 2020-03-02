package com.springgradlejwtsecurity.auth.service;

import com.springgradlejwtsecurity.auth.entity.Account;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityAccount extends User {
	private static final Long serialVersionUID = 1L;

	public SecurityAccount(Account account) {
		super(account.getUserId(),
			"{noop}" + account.getPassword(),
			AuthorityUtils.createAuthorityList(account.getPermission().toString()));
	}
}
