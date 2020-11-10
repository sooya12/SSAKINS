package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.dto.AccountAndProject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/main")
public class ProjectController {

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody AccountAndProject accountAndProject){
    	accountAndProject.getProject().getServers().forEach((s)->s.setType());
    	accountAndProject.getProject().getCredentials().forEach(c->c.setType());
    	
    	
        System.out.println(accountAndProject);
        System.out.println(accountAndProject.getProject());

        return ResponseEntity.ok().body("good");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity list() {
    	
        System.out.println("test");
        return ResponseEntity.ok().body("good");
    }
}

