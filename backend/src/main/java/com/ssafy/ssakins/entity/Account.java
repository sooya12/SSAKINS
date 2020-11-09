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

    private List<Project> project;


    public void addProject(Project p){
        getProjectsInternal().add(p);
    }

    private List<Project> getProjectsInternal(){
        if(this.project==null){
            this.project=new ArrayList<>();
        }
        return this.project;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", project=" + project +
                '}';
    }
}
