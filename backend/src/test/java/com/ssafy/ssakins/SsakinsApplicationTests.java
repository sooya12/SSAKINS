package com.ssafy.ssakins;

import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SsakinsApplicationTests {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void saveTest() {
        Account account = Account.builder()
                .email("naju1@lcy.com")
                .name("나주")
                .build();

        Assert.notNull(accountRepository.save(account));
    }

    @Test
    void findAll(){
        accountRepository.findAll().stream().forEach(System.out::println);
        Assert.notNull(accountRepository.findAll());
    }

}
