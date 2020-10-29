package com.ssafy.ssakins.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssakins.dto.AccountDoc;
import com.ssafy.ssakins.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value="/mongo")
	public String test() {
		List<AccountDoc> list = accountService.getAccountList("이채영");
		System.out.println(list);
		return "되니?";
	}


}
