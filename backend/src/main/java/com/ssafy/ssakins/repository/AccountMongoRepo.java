package com.ssafy.ssakins.repository;



import org.springframework.data.mongodb.repository.MongoRepository;


import com.ssafy.ssakins.dto.AccountDoc;

public interface AccountMongoRepo extends MongoRepository<AccountDoc, Integer>{
	
}
