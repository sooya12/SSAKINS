package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/mongo")
class MongoTestController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value="/selectAll", method = RequestMethod.GET)
    public void selectAll() throws IOException {
        System.out.println(accountRepository.findAll());
    }

}
