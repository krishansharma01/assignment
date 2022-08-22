package com.maxbit.assignment.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.maxbit.assignment.entity.UserApplication;
import com.maxbit.assignment.exception.ApplicationNotFoundException;
import com.maxbit.assignment.model.ApplicationResponse;
import com.maxbit.assignment.model.UserApplicationModel;

public interface ApplicationService {

	ApplicationResponse<List<UserApplication>> getAllApplications();

	ApplicationResponse<UserApplication> getApplicationByEmail(String email) throws ApplicationNotFoundException;

	ApplicationResponse<UserApplication> saveApplication(UserApplicationModel application);

	ByteArrayInputStream export();

	ByteArrayInputStream exportByEmail(String email);

}
