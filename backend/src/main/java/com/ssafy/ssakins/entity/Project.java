package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String name;
    private String url;
    private int port;
    private Git git;
    private SSHServer sshServer;
    private List<Plugin> plugins;
    private List<Credential> credentials;
    private Server servers;
    private List<GlobalTool> globalTools;
    private List<Command> commands;


    public void changeName(String name){
        this.name=name;
    }

    public void changeGit(Git git){ this.git=git; }
    
    public void changeUrl(String url) {
    	this.url=url;
    }
    
    public void changePort(int port) {
    	this.port=port;
    }

    public void addPlugin(Plugin plugin){
        getPluginsInternal().add(plugin);
    }

    public void addCredential(Credential credential){
        getCredentialsInternal().add(credential);
    }

    public void addServer(Server servers){ this.servers=servers; }

    public void addGlobalTool(GlobalTool globalTool){
        getGlobalToolsInternal().add(globalTool);
    }

    public void addCommand(Command command){
        getCommandsInternal().add(command);
    }



    private List<Plugin> getPluginsInternal(){
        if(this.plugins==null){
            this.plugins=new ArrayList<>();
        }
        return this.plugins;
    }
    private List<Credential> getCredentialsInternal(){
        if(this.credentials==null){
            this.credentials=new ArrayList<>();
        }
        return this.credentials;
    }
//    private List<Server> getServersInternal(){
//        if(this.servers==null){
//            this.servers=new ArrayList<>();
//        }
//        return this.servers;
//    }
    private List<GlobalTool> getGlobalToolsInternal(){
        if(this.globalTools==null){
            this.globalTools=new ArrayList<>();
        }
        return this.globalTools;
    }
    private List<Command> getCommandsInternal(){
        if(this.commands==null){
            this.commands=new ArrayList<>();
        }
        return this.commands;
    }


    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", git='" + git + '\'' +
                ", plugins=" + plugins +
                ", credentials=" + credentials +
                ", servers=" + servers +
                ", globalTools=" + globalTools +
                ", commands=" + commands +
                '}';
    }
}
