package com.ssafy.ssakins.repository;

import com.ssafy.ssakins.entity.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    Optional<Account> findByEmail(String email);


}
