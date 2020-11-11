package com.ssafy.ssakins.dto;

import com.ssafy.ssakins.entity.Project;
import lombok.Data;

@Data
public class AccountAndProject {
    String userEmail;
    Project project;
}
