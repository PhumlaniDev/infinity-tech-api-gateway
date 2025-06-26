package com.phumlanidev.apigateway.repository;

import com.phumlanidev.apigateway.entity.RateIpLimitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateIpLimitLogRepository extends JpaRepository<RateIpLimitLog, Long> {}
