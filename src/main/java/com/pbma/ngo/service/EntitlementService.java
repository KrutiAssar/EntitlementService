package com.pbma.ngo.service;

import org.springframework.http.ResponseEntity;

public interface EntitlementService {

	public ResponseEntity<String> saveEntitlement(final String entitlementRequest) throws Exception;

	public ResponseEntity<String> getEntitlement(final String userId) throws Exception;

	public ResponseEntity<String> getAllEntitlements() throws Exception;

	public ResponseEntity<String> updateEntitlements(final String entitlementsRequest) throws Exception;

}
