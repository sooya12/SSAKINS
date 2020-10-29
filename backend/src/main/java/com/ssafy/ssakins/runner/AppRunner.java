package com.ssafy.ssakins.runner;

import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

//    @Autowired
//    MongoTemplate mongoTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setEmail("subsub@ssafy.com");
        account.setName("코섭");

        accountRepository.insert(account);
    }
}
