package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer>, JpaSpecificationExecutor<Reviews> {
	// Add custom query methods here if needed
}