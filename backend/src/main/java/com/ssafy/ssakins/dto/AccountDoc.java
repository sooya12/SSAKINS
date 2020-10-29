package com.ssafy.ssakins.dto;




import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("account")
public class AccountDoc {

	@Id
	private int id;
	private String email;
	private String name;
	
	
	
}
