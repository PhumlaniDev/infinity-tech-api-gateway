package com.phumlanidev.apigateway.repository;

import com.phumlanidev.apigateway.entity.RateUserIdLimitLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateUserIdLimitLogRepository extends JpaRepository<RateUserIdLimitLog, Long> {
}
