package com.ms.bill_management_service.domain;

public record ElectricBillRequest(String idClient, String idService, String referenceNumber) {
}
