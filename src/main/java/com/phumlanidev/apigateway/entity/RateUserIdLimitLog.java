package com.phumlanidev.apigateway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Entity(name = "rate_user_id_limit_logs")
@Data
public class RateUserIdLimitLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String ipAddress;
  private String userId;
  private String endpoint;
  private Instant timestamp;

  {
    Instant.now();
    timestamp = Instant.now();
  }
}
