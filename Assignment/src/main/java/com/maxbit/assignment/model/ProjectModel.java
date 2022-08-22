package com.maxbit.assignment.model;

import org.springframework.lang.Nullable;

import com.maxbit.assignment.entity.CapacityType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectModel {

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
	
	private UserApplicationModel application;
}
