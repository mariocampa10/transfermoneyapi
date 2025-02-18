package com.demo.transfermoneyapi.repository;

import com.demo.transfermoneyapi.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}