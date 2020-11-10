package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.dto.AccountAndProject;
import org.springframework.http.HttpStatus;
import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.entity.Project;
import com.ssafy.ssakins.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/main")
public class ProjectController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity insert(@RequestBody AccountAndProject accountAndProject) {
        Account account = accountRepository.findByEmail(accountAndProject.getUserEmail()).get();
        accountAndProject.getProject().getServers().forEach((s)->s.setType());
        account.addProject(accountAndProject.getProject());
        accountRepository.save(account);

        return ResponseEntity.ok().body("save complete");
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity select(@PathVariable String email) {
        return ResponseEntity.ok().body(accountRepository.findByEmail(email).get());
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String email, @RequestBody Setg<String> projectName) {
        Account account = accountRepository.findByEmail(email).get();
        account.deleteProject(projectName);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{email}/{projectName}", method = RequestMethod.GET)
    public ResponseEntity selectProject(@PathVariable String email, @PathVariable String projectName) {
        Account account = accountRepository.findByEmail(email).get();
        Project project = new Project();

        for (Project p : account.getProject()) {
            if(projectName.equals(p.getName())) {
                project = p;
                break;
            }

            project = null;
        }

        return ResponseEntity.ok().body(project);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity list() {
    	
        System.out.println("test");
        return ResponseEntity.ok().body("good");
    }
}

