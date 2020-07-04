package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Statistics {

    private final String courseName;

    private final LocalDate enrollmentDate;

    private final BigDecimal amountToPay;

    private final BigDecimal paidAmount;

    private final BigDecimal studyingMonth;


}
