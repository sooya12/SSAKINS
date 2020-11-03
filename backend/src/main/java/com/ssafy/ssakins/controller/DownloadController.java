package com.ssafy.ssakins.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/download")
public class DownloadController {

    private ResourceLoader resourceLoader;

    public DownloadController(@Qualifier("webApplicationContext") ResourceLoader resourceLoader){
        this.resourceLoader=resourceLoader;
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
}
