package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @Min(value = 0, message = "Error: Amount cannot be negative!")
    private BigDecimal amount;

    @Transient
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @PrePersist
    @PreUpdate
    public void setDate() {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
}
