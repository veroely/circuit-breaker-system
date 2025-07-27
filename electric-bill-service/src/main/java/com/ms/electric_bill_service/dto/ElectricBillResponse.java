package com.ms.electric_bill_service.dto;

public record ElectricBillResponse(String reference, double amount, String dueDate, String customerIdentification, String customerName) {
}
