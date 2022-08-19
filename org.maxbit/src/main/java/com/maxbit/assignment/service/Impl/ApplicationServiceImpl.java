package com.maxbit.assignment.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.maxbit.assignment.exception.ApplicationNotFoundException;
import com.maxbit.assignment.model.ApplicationResponse;
import com.maxbit.assignment.model.Project;
import com.maxbit.assignment.model.UserApplication;
import com.maxbit.assignment.repository.ApplicationsRepository;
import com.maxbit.assignment.repository.ProjectRepository;
import com.maxbit.assignment.service.ApplicationService;
import com.maxbit.assignment.utililty.InvoiceDataPdfExport;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationsRepository repository;

	@Autowired
	private ProjectRepository projectRepository;

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
	public ApplicationResponse<UserApplication> saveApplication(UserApplication newApplication) {
		Optional<UserApplication> applicationOptional = repository.findByEmail(newApplication.getEmail());

		List<Project> projects = newApplication.getProjects();
		for (Project project : projects) {
			project.setApplication(newApplication);
		}

		if (applicationOptional.isPresent()) {
			repository.delete(applicationOptional.get());
			UserApplication application = applicationOptional.get();
			application.setGithubUserName(newApplication.getGithubUserName());
			application.setName(newApplication.getName());
			application.setProjects(newApplication.getProjects());

			repository.save(application);

		} else {
			repository.save(newApplication);
		}
		return new ApplicationResponse<>(HttpStatus.OK, "Successful! ");
	}

	@Override
	public ApplicationResponse<UserApplication> export() {
		ApplicationResponse<List<UserApplication>> response = getAllApplications();
		List<UserApplication> applications = response.getData();
		pdfExport.generatePdfReport(applications);
		return null;
	}

}
