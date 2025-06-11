package com.phumlanidev.apigateway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "rate_limit_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String ipAddress;
  private String path;
  private String method;
  private Instant timestamp;
}
