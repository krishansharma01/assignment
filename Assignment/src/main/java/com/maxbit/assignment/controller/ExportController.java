package com.maxbit.assignment.controller;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxbit.assignment.service.ApplicationService;

@RestController
@RequestMapping(value = "/api/v1/applications/export/", produces = MediaType.APPLICATION_PDF_VALUE)
public class ExportController {

	@Autowired
	ApplicationService applicationService;

	@GetMapping
	public ResponseEntity<InputStreamResource> export() {
		ByteArrayInputStream reportByteStream = applicationService.export();
		return ResponseEntity.ok().headers(getExportHeader()).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(reportByteStream));
	}

	@GetMapping(value = "/exportByEmail")
	public ResponseEntity<InputStreamResource> export(@RequestParam("email") String email) {
		ByteArrayInputStream reportByteStream = applicationService.exportByEmail(email);
		return ResponseEntity.ok().headers(getExportHeader()).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(reportByteStream));
	}

	private HttpHeaders getExportHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=report.pdf");
		return headers;
	}

}
