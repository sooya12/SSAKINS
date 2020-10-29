package com.ssafy.ssakins.repository;

import com.ssafy.ssakins.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    public Optional<Account> findById(String id);
}
