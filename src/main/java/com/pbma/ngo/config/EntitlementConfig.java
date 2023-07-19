package com.pbma.ngo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bazaarvoice.jolt.JsonUtils;

@Configuration
public class EntitlementConfig {

	@Autowired
	private ApplicationYaml applicationYaml;

	@Bean
	public List<Object> getEntitlementPostRequestJoltSpec() {
		return JsonUtils.classpathToList(applicationYaml.getJolt().getEntitlementPostRequestJoltSpec());
	}

	@Bean
	public List<Object> getEntitlementGetResponseJoltSpec() {
		return JsonUtils.classpathToList(applicationYaml.getJolt().getEntitlementGetResponseJoltSpec());
	}

	@Bean
	public List<Object> getEntitlementPostResponseJoltSpec() {
		return JsonUtils.classpathToList(applicationYaml.getJolt().getEntitlementPostResponseJoltSpec());
	}

	@Bean
	public List<Object> getEntitlementPutRequestJoltSpec() {
		return JsonUtils.classpathToList(applicationYaml.getJolt().getEntitlementPutRequestJoltSpec());
	}

}
