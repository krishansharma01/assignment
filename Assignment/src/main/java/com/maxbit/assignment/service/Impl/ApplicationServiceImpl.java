package com.maxbit.assignment.service.Impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.maxbit.assignment.Application;
import com.maxbit.assignment.entity.Project;
import com.maxbit.assignment.entity.UserApplication;
import com.maxbit.assignment.exception.ApplicationNotFoundException;
import com.maxbit.assignment.model.ApplicationResponse;
import com.maxbit.assignment.model.ProjectModel;
import com.maxbit.assignment.model.UserApplicationModel;
import com.maxbit.assignment.repository.ApplicationsRepository;
import com.maxbit.assignment.service.ApplicationService;
import com.maxbit.assignment.utililty.InvoiceDataPdfExport;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationsRepository repository;

	@Autowired
	private InvoiceDataPdfExport pdfExport;

	@Override
	public ApplicationResponse<List<UserApplication>> getAllApplications() {
		List<UserApplication> findAll = (List<UserApplication>) repository.findAll();
		return new ApplicationResponse<>(HttpStatus.OK, "Successful! ", findAll);
	}

	@Override
	public ApplicationResponse<UserApplication> getApplicationByEmail(String email)
			throws ApplicationNotFoundException {
		Optional<UserApplication> application = repository.findByEmail(email);
		if (application.isPresent())
			return new ApplicationResponse<>(HttpStatus.OK, "Successful! ", application.get());
		else
			throw new ApplicationNotFoundException("No Application not found for email : " + email);

	}

	@Override
	public ApplicationResponse<UserApplication> saveApplication(UserApplicationModel newApplication) {
		Optional<UserApplication> applicationOptional = repository.findByEmail(newApplication.getEmail());
		
		UserApplication application = mapToEntity(newApplication);
		if (applicationOptional.isPresent()) {
			repository.delete(applicationOptional.get());
			repository.save(application);

		} else {
			repository.save(application);
		}

		return new ApplicationResponse<>(HttpStatus.OK, "Successful! ");
	}

	private UserApplication mapToEntity(UserApplicationModel newApplication) {
		UserApplication application = new UserApplication(newApplication.getEmail(),newApplication.getName(),newApplication.getGithubUserName());
		List<Project> projects = new ArrayList<>();
		for (ProjectModel project : newApplication.getProjects()) {
			projects.add( new Project(project.getProjectName(),project.getType(),
					project.getDurationInMonths(),project.getStartYear(),project.getRole()
					,project.getTeamSize(),project.getGitHubLink(),project.getLiveUrl(),application));
		}
		application.setProjects(projects);
		return application;
	}

	@Override
	public ByteArrayInputStream export() {
		ApplicationResponse<List<UserApplication>> response = getAllApplications();
		List<UserApplication> applications = response.getData();
		return pdfExport.generatePdfReport(applications);
	}

	@Override
	public ByteArrayInputStream exportByEmail(String email) {
		ApplicationResponse<UserApplication> response = getApplicationByEmail(email);
		UserApplication application = response.getData();
		return pdfExport.generateApplicantPdfReport(application);
	}
}
