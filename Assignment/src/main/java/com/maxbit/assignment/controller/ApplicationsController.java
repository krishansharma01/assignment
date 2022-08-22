package com.maxbit.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxbit.assignment.entity.UserApplication;
import com.maxbit.assignment.model.ApplicationResponse;
import com.maxbit.assignment.model.UserApplicationModel;
import com.maxbit.assignment.service.ApplicationService;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationsController {

	@Autowired
	ApplicationService applicationService;

	@GetMapping(value = { "/", "" })
	public ApplicationResponse<List<UserApplication>> getApplications() {
		return applicationService.getAllApplications();
	}

	@GetMapping("getAppliationByEmail")
	public ApplicationResponse<UserApplication> getApplicationsByEmail(@RequestParam("email") String email) {
		return applicationService.getApplicationByEmail(email);
	}

	@PostMapping
	public ApplicationResponse<UserApplication> save(@RequestBody UserApplicationModel application) {
		return applicationService.saveApplication(application);
	}

}
