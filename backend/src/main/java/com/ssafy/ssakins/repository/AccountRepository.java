package com.ssafy.ssakins.repository;

import com.ssafy.ssakins.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, Integer> {
}
