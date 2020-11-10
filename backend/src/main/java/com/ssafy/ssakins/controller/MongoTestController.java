package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.dto.AccountAndProject;
import com.ssafy.ssakins.entity.*;
import com.ssafy.ssakins.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;


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
        accountAndProject.getProject().getServers().forEach((s)->s.setType());
        account.addProject(accountAndProject.getProject());
        accountRepository.save(account);
    }

    @RequestMapping(value = "/insertAccount", method = RequestMethod.POST)
    public void insertAccount(@RequestBody Account account) {
        accountRepository.save(account);
        System.out.println(accountRepository.findAll());
    }

    @RequestMapping(value = "/select/{email}", method = RequestMethod.GET)
    public ResponseEntity select(@PathVariable String email) {
        return ResponseEntity.ok().body(accountRepository.findByEmail(email).get());
    }

    @RequestMapping(value = "/select/{email}/{projectName}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/infoFile/{email}/{projectName}", method = RequestMethod.GET)
    ResponseEntity infoFile(@PathVariable String email, @PathVariable String projectName) throws IOException {
        InputStreamResource isr = new InputStreamResource(new FileInputStream(getInfoFile(email, projectName)));
        return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"accountInfo.sh\"").body(isr);
    }

    public File getInfoFile(String email, String projectName) {
        Account account = accountRepository.findByEmail(email).get();

        File file = new File("accountInfo"); // 파일 객체 생성

        try {
            FileWriter fw = new FileWriter(file, false); // 기존 내용에 이어서 작성
            Project project = new Project();

            for (Project p : account.getProject()) {
                if(projectName.equals(p.getName())) {
                    project = p;
                    break;
                }

                project = null;
            }

            if(!project.equals(null)) {
                fw.write("PROJECTNAME=" + project.getName() + "\n");
                fw.write("URL=" + project.getUrl() + "\n");
                fw.write("PORT=" + project.getPort() + "\n");
                
                Git git = project.getGit();
                
                fw.write("GITID=" + git.getId() + "\n");
                fw.write("GITUSERNAME=" + git.getName() + "\n");
                fw.write("GITPASSWORD=" + git.getPassword() + "\n");
                fw.write("GITURL=" + git.getGiturl() + "\n");
                fw.write("GITTYPE=" + git.getType() + "\n");
                fw.write("GITCREDENTIAL=" + git.getId() + "\n"); // git-configuration.xml - CredentialsId

                SSHServer sshServer = project.getSshServer();

                fw.write("SSHNAME=" + sshServer.getName() + "\n");
                fw.write("IPADDRESS=" + sshServer.getHostName() + "\n");
                fw.write("SERVERUSERNAME=" + sshServer.getUserName() + "\n");
                fw.write("SERVERPASSWORD=" + sshServer.getPassword() + "\n");
                fw.write("PEMKEY=" + sshServer.getKey() + "\n");
                fw.write("REMOTEDIRECTORY=" + sshServer.getRemoteDirectory() + "\n");

                for (Server server : project.getServers()) {
                    if("Vue".equals(server.getKind())) {
                        fw.write("FRONTNAME=" + server.getName() + "\n");
                        fw.write("FRONTLOCATION=" + server.getInfo() + "\n");
                        fw.write("FRONTPORT=" + server.getPort() + "\n");
                    } else if("Spring".equals(server.getKind())) {
                        fw.write("BACKNAME=" + server.getName() + "\n");
                        fw.write("BACKLOCATION=" + server.getInfo() + "\n");
                        fw.write("BACKPORT=" + server.getPort() + "\n");
                    }
                }
            }

            fw.flush();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

}
