package com.ssafy.ssakins;

import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest
class ProjectTest {
    @Autowired
    AccountRepository accountRepository;

//    @Test
//    void makeAccount(){
//        Account account = Account.builder()
//                .name("챙")
//                .email("goguma@ssafy.com")
//                .build();
//        Assert.notNull(accountRepository.save(account),"저장 안됨.");
//    }

    @Test
    void getAccount(){
//        System.out.println(accountRepository.findAll());
        System.out.println(accountRepository.findByEmail("sooya@ssakins.com").get());

//        Assert.notNull(accountRepository.findByEmail("lc@lc.com").get(),"null임");
    }

//    @Test
//    void addProject(){
//
//    }
}
