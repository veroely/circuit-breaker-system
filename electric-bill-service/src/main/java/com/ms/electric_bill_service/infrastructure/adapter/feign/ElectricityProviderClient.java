package com.ms.electric_bill_service.infrastructure.adapter.feign;

import com.ms.electric_bill_service.dto.ElectricBillResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "electricityProvider", url = "${external-services.electricity-provider.url}")
public interface ElectricityProviderClient {

    @GetMapping("/api/electricity/bill")
    ElectricBillResponse getBill(@RequestParam("reference") String reference);
}
