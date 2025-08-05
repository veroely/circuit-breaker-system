package com.ms.payment_management_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "electricityService", url = "${microservices.electric-bill-service.url}")
public interface ElectricBillClient {
    @GetMapping("/api/electricity/{providerId}/bill/{referenceNumber}")
    ElectricBillResponse getBill(@PathVariable("providerId") String providerId, @PathVariable("referenceNumber") String referenceNumber);
}