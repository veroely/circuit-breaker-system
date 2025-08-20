package com.ms.payment_management_service;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ElectricBillResponse (String reference, BigDecimal amount, LocalDate dueDate, String customerIdentification, String customerName) {}
