package com.ssafy.ssakins.controller;

import com.ssafy.ssakins.entity.*;
import com.ssafy.ssakins.repository.AccountRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
