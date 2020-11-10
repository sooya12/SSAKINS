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
                fw.write("\n");

                fw.write("# Groovy \n");
                fw.write("URL=" + project.getUrl() + "\n");
                fw.write("PORT=" + project.getPort() + "\n");
                fw.write("\n");


                Git git = project.getGit();

                String url = git.getGiturl();
                int idx = url.indexOf(".com");
                String configurl = url.substring(0, idx+5);

                fw.write("# Git \n");
                fw.write("GITID=" + git.getId() + "\n");
                fw.write("GITUSERNAME=" + git.getName() + "\n");
                fw.write("GITPASSWORD=" + git.getPassword() + "\n");
                fw.write("GITURL=" + git.getGiturl() + "\n");
                fw.write("GITKIND=" + git.getType() + "\n");
                fw.write("GITCREDENTIAL=" + git.getId() + "\n"); // git-configuration.xml - CredentialsId
                fw.write("\n");

                fw.write("# GitLabConnectionConfig.xml \n");
                fw.write("GITLABCONFIGNAME=" + git.getId() + "\n");
                fw.write("GITLABCONFIGURL=" + configurl + "\n");
                fw.write("# GITLABCONFIGCREDENTIAL는 Groovy 사용 \n");
                fw.write("\n");

                fw.write("# github-plugin-configuration.xml \n");
                fw.write("GITHUBCONFIGNAME=" + git.getId() + "\n");
                fw.write("GITHUBCONFIGURL=" + configurl + "\n");
                fw.write("# GITHUBCONFIGCREDENTIAL는 Groovy 사용 \n");
                fw.write("\n");

                SSHServer sshServer = project.getSshServer();

                fw.write("# BapSshPublisherPlugin.xml \n");
                fw.write("SSHNAME=" + project.getName() + "\n");
                fw.write("IPADDRESS=" + sshServer.getHostName() + "\n");
                fw.write("SERVERUSERNAME=" + sshServer.getUserName() + "\n");
                fw.write("SERVERPASSWORD=" + sshServer.getPassword() + "\n");
                fw.write("PEMKEY=" + sshServer.getKey() + "\n");
                fw.write("# REMOTEDIRECTORY는 pwd로 deploy 경로 조회해서 사용 \n");
                fw.write("\n");

                fw.write("# jobs.item.config.xml \n");
                fw.write("ITEM=" + project.getName() + "\n");
                fw.write("GITREPOSITORYURL=" + git.getGiturl().substring(0, git.getGiturl().length() - 4) + "\n");
                fw.write("GITREPOSITORYGIT=" + git.getGiturl() + "\n");
                fw.write("GITCREDENTIAL=" + git.getId() + "\n");
                fw.write("CONFIGNAME=" + project.getName() + "\n");
                fw.write("\n");

                for (Server server : project.getServers()) {
                    if("Vue".equals(server.getKind())) {
                        fw.write("# Front Infomation \n");
                        fw.write("FRONTLOCATION=" + server.getInfo() + "\n");
                        fw.write("FRONTPORT=80 \n");
                        fw.write("SOURCEFILE=" + server.getInfo() + "/dist \n");
                        fw.write("FRONTREMOVEPREFIX=" + server.getInfo() + "/ \n");
                        fw.write("FRONTEXECCOMMAND='sh 절대경로/jenkins_home/remoteDirectory/deploy-vue.sh' \n");
                        fw.write("\n");
                    } else if("Spring".equals(server.getKind())) {
                        fw.write("# Back Infomation \n");
                        fw.write("BACKLOCATION=" + server.getInfo() + "\n");
                        fw.write("BACKPORT=" + server.getPort() + "\n");
                        fw.write("POMXMLLOCATION=" + server.getInfo() + "/pom.xml \n");
                        fw.write("SOURCEFILE=" + server.getInfo() + "/target/*.jar \n");
                        fw.write("BACKREMOVEPREFIX=" + server.getInfo() + "/target/ \n");
                        fw.write("BACKEXECCOMMAND='sh 절대경로/jenkins_home/remoteDirectory/deploy-spring.sh' \n");
                        fw.write("\n");
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
