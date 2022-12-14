package com.maxbit.assignment.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Transient;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Transient
	private Long applicationId;

	@NonNull
	private String email;

	@NonNull
	private String name;
	
	@NonNull
	private String githubUserName;

	@NonNull
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "application")
	private List<Project> projects;

	public UserApplication(String email, String name, String githubUserName, List<Project> projects) {
		super();
		this.email = email;
		this.name = name;
		this.githubUserName = githubUserName;
		this.projects = projects;
	}
	
	public UserApplication(String email, String name, String githubUserName) {
		super();
		this.email = email;
		this.name = name;
		this.githubUserName = githubUserName;
	}
}
