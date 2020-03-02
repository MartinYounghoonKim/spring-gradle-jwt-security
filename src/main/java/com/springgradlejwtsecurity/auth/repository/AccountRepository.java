package com.springgradlejwtsecurity.auth.repository;

import com.springgradlejwtsecurity.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findAccountByUserId (String userId);
}
