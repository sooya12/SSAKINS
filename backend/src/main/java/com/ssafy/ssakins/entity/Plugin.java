package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Plugin {
    private PluginKind kind;
    private String name;
    private String version;

    @Override
    public String toString() {
        return "Plugin{" +
                "kind=" + kind +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
