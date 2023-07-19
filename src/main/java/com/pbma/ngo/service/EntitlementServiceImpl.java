package com.pbma.ngo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbma.ngo.config.EntitlementConfig;
import com.pbma.ngo.entity.Entitlement;
import com.pbma.ngo.repository.EntitlementRepository;
import com.pbma.ngo.util.Constants;
import com.pbma.ngo.util.EntitlementUtils;

@Service
public class EntitlementServiceImpl implements EntitlementService {

	@Autowired
	private EntitlementConfig entitlementConfig;

	@Autowired
	private EntitlementRepository entitlementRepository;

	private static final Logger entitlementLogger = LoggerFactory.getLogger(EntitlementServiceImpl.class);

	@Override
	public ResponseEntity<String> saveEntitlement(final String entitlementRequest) throws Exception {

		// jolt for request json - flatten to map to entity
		String transformedEntitlementRequest = EntitlementUtils.transformRequest(entitlementRequest,
				entitlementConfig.getEntitlementPostRequestJoltSpec());
		entitlementLogger.debug("Save Entitlement transformed request : {}", transformedEntitlementRequest);

		ObjectMapper objectMapper = new ObjectMapper();
		Entitlement requestEntitlementObject = objectMapper.readValue(transformedEntitlementRequest, Entitlement.class);

		Calendar calendar = Calendar.getInstance();
		calendar.clear(Calendar.ZONE_OFFSET);

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		requestEntitlementObject.setCreationTimestamp(timestamp);
		requestEntitlementObject.setLastUpdateTimestamp(timestamp);

		Entitlement trainee = entitlementRepository.save(requestEntitlementObject);
		entitlementLogger.info("Trainee Details inserted in database successfully");

		// response jolt for traineeId
		String traineeResponse = new JSONObject(trainee).toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);

		String response = new JSONObject(EntitlementUtils.transformRequest(traineeResponse,
				entitlementConfig.getEntitlementPostResponseJoltSpec()))
				.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		entitlementLogger.debug("Save Entitlement transformed response : {}", response);

		return new ResponseEntity<String>(response, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> getEntitlement(String userId) throws Exception {

		Entitlement trainee = entitlementRepository.findByUserId(userId);
		entitlementLogger.info("Entitlement retrieved from database successfully");

		String entitlementResponse = new JSONObject(trainee).toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);

		String response = new JSONObject(EntitlementUtils.transformRequest(entitlementResponse,
				entitlementConfig.getEntitlementGetResponseJoltSpec()))
				.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		entitlementLogger.debug("Get Entitlement transformed response : {}", response);

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<String> getAllEntitlements() throws Exception {

		List<Entitlement> entitlements = entitlementRepository.findAll();
		entitlementLogger.info("All Entitlements retrieved from database successfully");

		JSONArray transformedEntitlements = new JSONArray();

		entitlements.forEach(entitlement -> {

			JSONObject responseEntitlement = new JSONObject(EntitlementUtils.transformRequest(
					new JSONObject(entitlement).toString(Constants.JSON_OBJECT_INDENTATION_FACTOR),
					entitlementConfig.getEntitlementGetResponseJoltSpec()));
			transformedEntitlements.put(responseEntitlement);

		});

		String response = transformedEntitlements.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		entitlementLogger.debug("Get All Entitlements transformed response : {}", response);

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<String> updateEntitlements(final String entitlementsRequest) throws Exception {

		// create update request and save details
		String transformedEntitlementsRequest = EntitlementUtils.transformRequest(entitlementsRequest,
				entitlementConfig.getEntitlementPutRequestJoltSpec());
		entitlementLogger.debug("Update Entitlements transformed request : {}", transformedEntitlementsRequest);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Entitlement> requestEntitlementsList = objectMapper.readValue(transformedEntitlementsRequest,
				new TypeReference<List<Entitlement>>() {
				});

		List<String> userIds = new ArrayList<>();

		requestEntitlementsList.forEach(requestEntitlementObject -> {

			Calendar calendar = Calendar.getInstance();
			calendar.clear(Calendar.ZONE_OFFSET);
			Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
			requestEntitlementObject.setLastUpdateTimestamp(timestamp);

			userIds.add(requestEntitlementObject.getUserId());

		});

		entitlementRepository.saveAll(requestEntitlementsList);
		entitlementLogger.info("Entilements updated in database successfully");

		// retrieve updated details from database
		JSONArray entitlementsJsonArray = new JSONArray();

		userIds.forEach(userId -> {
			try {
				entitlementsJsonArray.put(new JSONObject(this.getEntitlement(userId).getBody()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		System.err.println();
		String response = entitlementsJsonArray.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		return new ResponseEntity<String>(response, HttpStatus.OK);
		
	}

}
