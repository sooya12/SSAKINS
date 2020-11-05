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

    private CredentialKind type;
    private String kind;
    private boolean isHub;
    private String name;
    private String id;
    private String password;
    private String apiKey;
    private String appID;
    private String key;
    private String username;
    private String passphrase;

    public void setType() {
    	switch (this.kind) {
		case "Username_with_password":
			this.type=CredentialKind.Username_with_password;
			break;
		case "GitHup_App":
			this.type=CredentialKind.GitHup_App;
			break;
		case "GitLap_API_token":
			this.type=CredentialKind.GitLap_API_token;
			break;
		case "SSH_Username_with_private_key":
			this.type=CredentialKind.SSH_Username_with_private_key;
			break;
		case "Secret_file":
			this.type=CredentialKind.Secret_file;
			break;
		case "Secret_text":
			this.type=CredentialKind.Secret_text;
			break;
		case "Certificate":
			this.type=CredentialKind.Certificate;
			break;
		default:
			this.type=null;
			break;
		}
    }

	@Override
	public String toString() {
		return "Credential [type=" + type + ", kind=" + kind + ", name=" + name + ", id=" + id + ", password="
				+ password + ", apiKey=" + apiKey + ", appID=" + appID + ", key=" + key + ", username=" + username
				+ ", passphrase=" + passphrase + "]";
	}

    
}
