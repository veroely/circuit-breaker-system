package com.ms.payment_management_service.infrastructure.adapter.input.feign;

import com.ms.payment_management_service.domain.TransactionRequest;
import com.ms.payment_management_service.domain.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transaction-service", url = "${transaction.service.url}")
public interface TransactionServiceClient {

    @PostMapping("/api/v1/transactions")
    TransactionResponse createTransaction(@RequestBody TransactionRequest request);
}
