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
    private String appKey;
    private String appID;
    private String key;
    private String username;
    private String passphrase;


}
