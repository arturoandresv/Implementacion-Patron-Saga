package com.ecommerce.paymentservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;

    private BigDecimal amount;
    private Status status;
    private Timestamp createdAt;

}
