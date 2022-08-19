package com.maxbit.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.maxbit.assignment.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	void deleteByApplication(Long applicationId);


}
