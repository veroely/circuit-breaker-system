package com.ms.bill_management_service.infraestructure.adapter.rest;

import com.ms.bill_management_service.application.port.input.BillManagementService;
import com.ms.bill_management_service.domain.BillRequest;
import com.ms.bill_management_service.domain.BillResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bills")
public class BillManagementController {
    private final BillManagementService billManagementService;

    public BillManagementController(BillManagementService billManagementService) {
        this.billManagementService = billManagementService;
    }


    @PostMapping
    @RequestMapping("/query")
    public BillResponse getBill(@RequestBody BillRequest request)
    {
        return billManagementService.getBillDetails(request);
    }
}
