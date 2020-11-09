package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.dto.AccountAndProject;
import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.entity.Git;
import com.ssafy.ssakins.entity.Project;
import com.ssafy.ssakins.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/mongo")
class MongoTestController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public Object selectAll() {
        System.out.println(accountRepository.findAll());
        return accountRepository.findAll();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(@RequestBody AccountAndProject accountAndProject) {
        Account account = accountRepository.findByEmail(accountAndProject.getUserEmail()).get();
        account.addProject(accountAndProject.getProject());
        accountRepository.save(account); 
    }

    @RequestMapping(value = "/insertAccount", method = RequestMethod.POST)
    public void insertAccount(@RequestBody Account account) {
        accountRepository.save(account);
        System.out.println(accountRepository.findAll());
    }

}
