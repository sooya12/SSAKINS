package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.dto.AccountAndProject;
import org.springframework.http.HttpStatus;
import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.entity.Project;
import com.ssafy.ssakins.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/project")
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



    @RequestMapping(value = "delete/{email}", method = RequestMethod.POST)
    public ResponseEntity delete(@PathVariable String email, @RequestBody List<String> projectName) {
        Account account = accountRepository.findByEmail(email).get();
        account.deleteProject(projectName.stream().collect(Collectors.toSet()));
        accountRepository.save(account);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{email}/{projectName}", method = RequestMethod.GET)
    public ResponseEntity selectProject(@PathVariable String email, @PathVariable String projectName) {
        Account account = accountRepository.findByEmail(email).get();
        Project project = new Project();

        for (Project p : account.getProject()) {
            if(projectName.equals(p.getName())) {
                System.out.println(p.toString());
                return ResponseEntity.ok().body(p);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/check/{email}/{projectName}", method = RequestMethod.GET)
    public ResponseEntity selectProjectName(@PathVariable String email, @PathVariable String projectName) {
        Account account = accountRepository.findByEmail(email).get();
        for (Project project : account.getProject()) {
            if(projectName.equals(project.getName())) {
                return ResponseEntity.badRequest().body("duplication");
            }
        }
        return ResponseEntity.ok().body("ok");
    }


}

