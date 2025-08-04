package com.ms.payment_management_service;

public record ElectricBillRequest(String idClient, String idService, String referenceNumber) {
}
