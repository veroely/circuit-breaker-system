package com.ms.payment_management_service.domain;

public record ElectricBillRequest(String idClient, String idService, String referenceNumber) {
}
