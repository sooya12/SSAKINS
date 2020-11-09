package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Git {
    private String id;
    private String name;
    private String password;
    private String giturl;
    private String type; // gitlab 또는 github

    public void changeId(String id) {
        this.id = id;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeGiturl(String giturl) {
        this.giturl = giturl;
    }

    public void changeType(String Type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Git{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", giturl='" + giturl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
