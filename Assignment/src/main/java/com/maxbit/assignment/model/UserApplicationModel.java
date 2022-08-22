package com.maxbit.assignment.model;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserApplicationModel {

	@NonNull
	private String email;

	@NonNull
	private String name;
	
	@NonNull
	private String githubUserName;

	private List<ProjectModel> projects;

}
