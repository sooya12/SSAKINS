package com.ssafy.ssakins.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ssafy.ssakins.dto.AccountDoc;

@Service
public class AccountService {

	@Autowired
	private MongoTemplate mongoTemplate;
//	public AccountDoc getAccount(String _id) {
//		AccountDoc account = mongoTemplate.findById(_id, AccountDoc.class);
//		return Optional.ofNullable(account).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Not found account"));
//	}
	public List<AccountDoc> getAccountList(String name){
		Query query = new Query().addCriteria(Criteria.where("name").is(name));
		return mongoTemplate.find(query, AccountDoc.class);
	}
	
	public AccountDoc insertAccount(AccountDoc account) {
		return mongoTemplate.insert(account);
	}
	
	
//	@Autowired
//	private AccountMongoRepo accountRepo;
//	
//	public List<AccountDoc> getAccountList(String name){
//		return accountRepo.findByName(name);
//	}
//	public AccountDoc insertAccount(AccountDoc account) {
//		return accountRepo.insert(account);
//	}
	
}
