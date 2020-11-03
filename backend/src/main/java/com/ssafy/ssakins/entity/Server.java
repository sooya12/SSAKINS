package com.ssafy.ssakins.entity;

public class Server {
    ServerKind kind;
    String name;
    String info;
    int port;

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
