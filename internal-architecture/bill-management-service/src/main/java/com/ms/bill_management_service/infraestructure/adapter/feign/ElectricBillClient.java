package com.ms.bill_management_service.infraestructure.adapter.feign;

import com.ms.bill_management_service.domain.ElectricBillResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "electricityService", url = "${internal-services.electric-bill-service.url}")
public interface ElectricBillClient {
    @GetMapping("/api/electricity/{providerId}/bill/{referenceNumber}")
    ElectricBillResponse getBill(@PathVariable("providerId") String providerId, @PathVariable("referenceNumber") String referenceNumber);
}