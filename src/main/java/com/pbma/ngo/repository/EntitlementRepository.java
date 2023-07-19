package com.pbma.ngo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbma.ngo.entity.Entitlement;

@Repository
public interface EntitlementRepository extends JpaRepository<Entitlement, Long> {

	public Entitlement findByUserId(@Param("userId") final String userId);

}
