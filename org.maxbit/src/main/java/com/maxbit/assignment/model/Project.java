package com.maxbit.assignment.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;

	@NonNull
	private String projectName;
	
	@NonNull
	private CapacityType type;
	
	@NonNull
	private Double durationInMonths;
	
	@NonNull
	private Integer startYear;
	
	@NonNull
	private String role;
	
	@NonNull
	private Integer teamSize;

	@Nullable
	private String gitHubLink;
	
	@Nullable
	private String liveUrl;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "application_id", nullable = false)
	@JsonBackReference
	private UserApplication application;
}
