package com.ms.electric_bill_service.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ElectricBill(String reference, BigDecimal amount, LocalDate dueDate, String customerIdentification, String customerName) {}
