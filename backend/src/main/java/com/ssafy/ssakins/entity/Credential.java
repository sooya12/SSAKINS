package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Credential {
    /**
        All scope is a global.
     */

    private CredentialKind kind;
    private String name;
    private String id;
    private String password;
    private String apiKey;
    private String appID;
    private String key;
    private String username;
    private String passphrase;


    @Override
    public String toString() {
        return "Credential{" +
                "kind=" + kind +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", appID='" + appID + '\'' +
                ", key='" + key + '\'' +
                ", username='" + username + '\'' +
                ", passphrase='" + passphrase + '\'' +
                '}';
    }
}
