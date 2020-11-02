package com.ssafy.ssakins;

import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringBootTest
class SsakinsApplicationTests {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void findByEmail(){
        Optional<Account> account = accountRepository.findByEmail("a@b.com");

        if(account.isPresent()){
            System.out.println(account.get().toString());
        }

        Assert.notNull(accountRepository.findByEmail("a@b.com"),"null이 아니다");
    }

//    @Test
//    void findByEmail2(){
//        Optional<Account> account = accountRepository.findByEmail("a@a.com");
//
//        if(account.isPresent()){
//            System.out.println(account.get().toString());
//        }
//
//        Assert.notNull(accountRepository.findByEmail("a@a.com").get(),"있다");
//    }

//    @Test
//    void saveTest() {
//        Account account = Account.builder()
//                .email("a@a.com")
//                .name("AAA")
//                .build();
//
//        Assert.notNull(accountRepository.save(account));
//    }
//
//    @Test
//    void findAll(){
//        accountRepository.findAll().stream().forEach(System.out::println);
//        Assert.notNull(accountRepository.findAll());
//    }

}
