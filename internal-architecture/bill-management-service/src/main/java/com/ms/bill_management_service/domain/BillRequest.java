package com.ms.bill_management_service.domain;

public record BillRequest(String idClient, String serviceType, String idService, String referenceNumber) {
}
