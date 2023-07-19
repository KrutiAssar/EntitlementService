package com.pbma.ngo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbma.ngo.service.EntitlementService;

@RestController
public class EntitlementController {

	@Autowired
	private EntitlementService entitlementService;

	private static final Logger entitlementLogger = LoggerFactory.getLogger(EntitlementController.class);

	@PostMapping(value = "/entitlements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addEntitlement(@RequestBody String entitlementRequest) throws Exception {
		entitlementLogger.info("Received request to Add Entitlement");
		ResponseEntity<String> responseEntity = entitlementService.saveEntitlement(entitlementRequest);
		entitlementLogger.info("Completed request to Add Entitlement");
		return responseEntity;
	}

	@GetMapping(value = "/entitlements/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> viewEntitlement(@PathVariable("userId") String userId) throws Exception {
		entitlementLogger.info("Received request to View Entitlement");
		ResponseEntity<String> responseEntity = entitlementService.getEntitlement(userId);
		entitlementLogger.info("Completed request to View Entitlement");
		return responseEntity;
	}

	@GetMapping(value = "/entitlements", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> viewAllEntitlements() throws Exception {
		entitlementLogger.info("Received request to View All Entitlements");
		ResponseEntity<String> responseEntity = entitlementService.getAllEntitlements();
		entitlementLogger.info("Completed request to View All Entitlements");
		return responseEntity;
	}

	@PutMapping(value = "/entitlements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> editEntitlements(@RequestBody String entitlementsRequest) throws Exception {
		entitlementLogger.info("Received request to Edit Entitlements");
		ResponseEntity<String> responseEntity = entitlementService.updateEntitlements(entitlementsRequest);
		entitlementLogger.info("Completed request to Edit Entitlements");
		return responseEntity;
	}

}
