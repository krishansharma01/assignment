package com.maxbit.assignment.utililty;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.maxbit.assignment.entity.CapacityType;
import com.maxbit.assignment.model.ProjectModel;
import com.maxbit.assignment.model.UserApplicationModel;
import com.maxbit.assignment.service.ApplicationService;

@Component
public class DemoData {

	@Autowired
	ApplicationService service;

	@EventListener
	public void appReady(ApplicationReadyEvent event) {

		List<ProjectModel> asList = Arrays.asList(
				new ProjectModel("Trade Finance", CapacityType.FULL_TIME, 12.0, 2019, "Developer", 4, "github.com",
						"NA", null),
				new ProjectModel("IRM", CapacityType.FULL_TIME, 12.0, 2019, "Developer", 4, "github.com", "NA", null));
		UserApplicationModel application = new UserApplicationModel("krishansandilya@gmail.com", "Krishan Sharma",
				"krishansharma01", asList);
		service.saveApplication(application);
	}
}