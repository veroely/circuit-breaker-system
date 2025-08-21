package com.ms.bill_management_service.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ElectricBillResponse (String reference, BigDecimal amount, LocalDate dueDate, String customerIdentification, String customerName) {}
