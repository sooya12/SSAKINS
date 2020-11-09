package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Command {
    private CommandType type;
    private String command;
    private String pom;


    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", command='" + command + '\'' +
                ", pom='" + pom + '\'' +
                '}';
    }
}
