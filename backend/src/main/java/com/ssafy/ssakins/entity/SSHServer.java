package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SSHServer {
    private String key;
    private String name;
    private String userName;
    private String hostName;
    private String remoteDirectory ;
    private String password;

    @Override
    public String toString() {
        return "SSHServer{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", remoteDirectory='" + remoteDirectory + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
