package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Server {
    private ServerKind type;
    private String kind;
    private String name;
    private String info;
    private int port;

    public void setType() {
    	switch (this.kind) {
		case "Spring":
			this.type=ServerKind.Spring;
			break;
		case "Django":
			this.type=ServerKind.Django;
			break;
		case "Flask":
			this.type=ServerKind.Flask;
			break;
		case "Express":
			this.type=ServerKind.Express;
			break;
		case "Vue":
			this.type=ServerKind.Vue;
			break;
		case "React":
			this.type=ServerKind.React;
			break;

		default:
			this.type=null;
			break;
		}
    }
    
    @Override
    public String toString() {
        return "Server{" +
                "kind=" + kind +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", port=" + port +
                '}';
    }
}
