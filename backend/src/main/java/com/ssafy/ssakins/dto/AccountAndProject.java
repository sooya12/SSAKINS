package com.ssafy.ssakins.dto;

import com.ssafy.ssakins.entity.Project;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class AccountAndProject {
    String userEmail;
    Project project;
}
