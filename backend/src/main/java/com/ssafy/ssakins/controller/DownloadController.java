package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.entity.*;
import com.ssafy.ssakins.repository.AccountRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URI;
import java.util.Deque;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/download")
public class DownloadController {

    private ResourceLoader resourceLoader;
    private AccountRepository accountRepository;


    public DownloadController(@Qualifier("webApplicationContext") ResourceLoader resourceLoader,AccountRepository accountRepository){
        this.resourceLoader=resourceLoader;
        this.accountRepository=accountRepository;
    }
    
    @RequestMapping(value="/{email}/{projectName}", method=RequestMethod.GET)
    ResponseEntity download(@PathVariable String email, @PathVariable String projectName) throws IOException{

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        Deque<File> files = new LinkedList<>();
        URI base = resourceLoader.getResource("classpath:static/").getFile().toURI();
        Resource resource = resourceLoader.getResource("classpath:static/ssakins/");
        files.add(resource.getFile());
        while (!files.isEmpty()) {
            File dir =files.pop();
            //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
            for(File file : dir.listFiles()) {
                String name = base.relativize(file.toURI()).getPath();
                if (file.isDirectory()) {
                    files.push(file);
                    //IOUtils.copy(fileInputStream, zipOutputStream);
                    name = name.endsWith("/")?name:name+"/";
                    zipOutputStream.putNextEntry(new ZipEntry(name));
                    //zipOutputStream.closeEntry();
                    if(file.getName().equals("ssakins_home")){
                        FileInputStream fileInputStream = new FileInputStream(getInfoFile(email, projectName));
                        zipOutputStream.putNextEntry(new ZipEntry(name+"accountInfo.sh"));
                        IOUtils.copy(fileInputStream, zipOutputStream);
                        fileInputStream.close();
                        zipOutputStream.closeEntry();
                    }

                } else {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    zipOutputStream.putNextEntry(new ZipEntry(name));
                    IOUtils.copy(fileInputStream, zipOutputStream);
                    fileInputStream.close();
                    zipOutputStream.closeEntry();
                }
            }
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"ssakins.zip\"")
                .body(byteArrayOutputStream.toByteArray());

    }
    
    @RequestMapping(value="/test", method=RequestMethod.GET)
    ResponseEntity test() throws FileNotFoundException {
    	InputStreamResource isr = new InputStreamResource(new FileInputStream(getDataFile()));
    	return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"data\"").body(isr);
    }
    
    public File getDataFile() {
    	File file = new File("Data");
    	String str = "진행해"; 
    	OutputStream out = null;
    	try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(str);
			bw.close();
			FileInputStream fileInputStream = new FileInputStream(file);
			IOUtils.copy(fileInputStream, out);
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return file;
    }
    

    @RequestMapping(value = "/zip", produces="application/zip", method = RequestMethod.GET)
    public ResponseEntity<byte[]> jenkins() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        Deque<File> files = new LinkedList<>();
        URI base = resourceLoader.getResource("classpath:static/").getFile().toURI();
        Resource resource = resourceLoader.getResource("classpath:static/ssakins/");
        files.add(resource.getFile());
        while (!files.isEmpty()) {
            File dir =files.pop();
            //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
            for(File file : dir.listFiles()) {
                String name = base.relativize(file.toURI()).getPath();
                if (file.isDirectory()) {
                    files.push(file);
                    //IOUtils.copy(fileInputStream, zipOutputStream);
                    name = name.endsWith("/")?name:name+"/";
                    zipOutputStream.putNextEntry(new ZipEntry(name));
                    //zipOutputStream.closeEntry();
                } else {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    zipOutputStream.putNextEntry(new ZipEntry(name));
                    IOUtils.copy(fileInputStream, zipOutputStream);
                    fileInputStream.close();
                    zipOutputStream.closeEntry();
                }
            }
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"ssakins.zip\"")
                .body(byteArrayOutputStream.toByteArray());
    }

    public File getInfoFile(String email, String projectName) {
        Account account = accountRepository.findByEmail(email).get();

        File file = new File("accountInfo"); // 파일 객체 생성

        try {
            FileWriter fw = new FileWriter(file, false); // false : 기존 내용에 이어서 작성하지 않음
            Project project = new Project();

            for (Project p : account.getProject()) {
                if(projectName.equals(p.getName())) {
                    project = p;
                    break;
                }

                project = null; // 해당 프로젝트 없음
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
                fw.write("GITKIND=" + git.getGitKind() + "\n");
                fw.write("GITCREDENTIAL=" + git.getId() + "\n"); // git-configuration.xml - CredentialsId
                fw.write("\n");

                if("gitlab".equals(git.getGitKind())) {
                    fw.write("# GitLabConnectionConfig.xml \n");
                    fw.write("GITLABCONFIGNAME=" + git.getId() + "\n");
                    fw.write("GITLABCONFIGURL=" + configurl + "\n");
                    fw.write("GITLABCONFIGCREDENTIAL=" + git.getPassword() + "\n");
                    fw.write("\n");
                } else {
                    fw.write("# github-plugin-configuration.xml \n");
                    fw.write("GITHUBCONFIGNAME=" + git.getId() + "\n");
                    fw.write("GITHUBCONFIGURL=" + configurl + "\n");
                    fw.write("GITHUBCONFIGCREDENTIAL=" + git.getPassword() + "\n");
                    fw.write("\n");
                }

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
                        fw.write("FRONTSOURCEFILE=" + server.getInfo() + "/dist \n");
                        fw.write("FRONTREMOVEPREFIX=" + server.getInfo() + "/ \n");
                        fw.write("FRONTEXECCOMMAND='sh 절대경로/jenkins_home/remoteDirectory/deploy-vue.sh' \n");
                        fw.write("\n");
                    } else if("Spring".equals(server.getKind())) {
                        fw.write("# Back Infomation \n");
                        fw.write("BACKLOCATION=" + server.getInfo() + "\n");
                        fw.write("BACKPORT=" + server.getPort() + "\n");
                        fw.write("POMXMLLOCATION=" + server.getInfo() + "/pom.xml \n");
                        fw.write("BACKSOURCEFILE=" + server.getInfo() + "/target/*.jar \n");
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
