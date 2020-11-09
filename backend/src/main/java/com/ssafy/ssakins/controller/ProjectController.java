package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.dto.AccountAndProject;
import com.ssafy.ssakins.entity.ServerKind;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/project")
public class ProjectController {

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody AccountAndProject accountAndProject){
//    	accountAndProject.getProject().getServers().forEach((s)->s.setType());
    	accountAndProject.getProject().getCredentials().forEach(c->c.setType());
    	
    	
        System.out.println(accountAndProject);
        System.out.println(accountAndProject.getProject());

        return ResponseEntity.ok().body("good");
    }
}

