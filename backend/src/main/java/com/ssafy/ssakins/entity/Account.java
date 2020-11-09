package com.ssafy.ssakins.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Document(collection = "account")
public class Account {


    private String email;

    private String name;

    private List<Project> projects;


    public void addProject(Project project){
        getProjectsInternal().add(project);
    }

    private List<Project> getProjectsInternal(){
        if(this.projects==null){
            this.projects=new ArrayList<>();
        }
        return this.projects;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", projects=" + projects +
                '}';
    }
}
