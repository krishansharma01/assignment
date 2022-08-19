package com.maxbit.assignment.service;

import java.util.List;

import com.maxbit.assignment.exception.ApplicationNotFoundException;
import com.maxbit.assignment.model.ApplicationResponse;
import com.maxbit.assignment.model.UserApplication;

public interface ApplicationService {

	ApplicationResponse<List<UserApplication>> getAllApplications();

	ApplicationResponse<UserApplication> getApplicationByEmail(String email) throws ApplicationNotFoundException;

	ApplicationResponse<UserApplication> saveApplication(UserApplication application);

	ApplicationResponse<UserApplication> export();

}
