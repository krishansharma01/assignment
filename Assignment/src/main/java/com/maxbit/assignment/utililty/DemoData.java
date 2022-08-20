package com.maxbit.assignment.utililty;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.maxbit.assignment.model.CapacityType;
import com.maxbit.assignment.model.Project;
import com.maxbit.assignment.model.UserApplication;
import com.maxbit.assignment.repository.ApplicationsRepository;
import com.maxbit.assignment.service.ApplicationService;

@Component
public class DemoData {

    @Autowired
    ApplicationService service;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        List<Project> asList = Arrays.asList(new Project("Trade Finance", CapacityType.FULL_TIME, 12.0, 2019, "Developer", 4,
				"github.com", "NA", null),
        		new Project("IRM", CapacityType.FULL_TIME, 12.0, 2019, "Developer", 4,
        				"github.com", "NA", null));
		UserApplication application = new UserApplication("krishansandilya@gmail.com","Krishan Sharma","krishansharma01",
				asList);
		for (Project project : asList) {
			project.setApplication(application);
		}
		service.saveApplication(application);
    }
}