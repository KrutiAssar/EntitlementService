package com.pbma.ngo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pbma.ngo.util.Constants;

import lombok.Data;

@Data
@Entity
@Table(name = "entitlement", schema = "student")
public class Entitlement {

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "trainee_id")
	private Long traineeId;

	@Column(name = "user_role")
	private String userRole;

	@Column(name = "entitlement")
	private String entitlement;

	@Column(name = "photo_url")
	private String photoUrl;

	@Column(name = "link_parameter")
	private String linkParameter;

	@Column(name = "link_value")
	private String linkValue;

	@Column(name = "status")
	private String status;

	@Column(name = "existing_user_ind")
	private String existingUserInd;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN, timezone = Constants.TIMEZONE_ASIA)
	@Column(name = "creation_timestamp")
	private Timestamp creationTimestamp;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN, timezone = Constants.TIMEZONE_ASIA)
	@Column(name = "last_update_timestamp")
	private Timestamp lastUpdateTimestamp;

}
