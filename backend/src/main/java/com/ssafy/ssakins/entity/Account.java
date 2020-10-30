package com.ssafy.ssakins.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document()
public class Account {


    private String email;

    private String name;

    @CreatedDate
    private LocalDateTime regdate;

    public Account() {}

    public Account(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", regdate=" + regdate +
                '}';
    }
}
