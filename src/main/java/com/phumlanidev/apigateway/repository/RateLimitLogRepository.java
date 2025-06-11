package com.phumlanidev.apigateway.repository;

import com.phumlanidev.apigateway.entity.RateLimitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitLogRepository extends JpaRepository<RateLimitLog, Long> {}
