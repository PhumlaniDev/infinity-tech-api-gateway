package com.phumlanidev.apigateway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "rate_ip_limit_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateIpLimitLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "ip_address", nullable = false)
  private String ipAddress;
  @Column(nullable = false)
  private String path;
  @Column(nullable = false, length = 10)
  private String method;
  @Column(nullable = false)
  private Instant timestamp;
}
