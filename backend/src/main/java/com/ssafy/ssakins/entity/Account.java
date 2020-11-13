package com.ssafy.ssakins.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Document(collection = "account")
public class Account {

    @Id
    private String email;

    private String name;

    private String image;

    private List<Project> project;

    public void addProject(Project p){
        getProjectsInternal().add(p);
    }

    public void deleteProject(Set<String> projectName ){
        project.removeIf(p->projectName.contains(p.getName()));
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
                "}\n";
    }
}
