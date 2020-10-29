package com.ssafy.ssakins.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collation = "account")
@Getter
@Setter
public class Account {
    @Id
    private int id;

    private String email;

    private String name;

    private Date regdate;

    public Account() {}

    public Account(int id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", regdate=" + regdate +
                '}';
    }
}
