package com.ms.payment_management_service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
public class PaymentController {
    private final ElectricBillClient electricBillClient;

    public PaymentController(ElectricBillClient electricBillClient) {
        this.electricBillClient = electricBillClient;
    }

    @PostMapping
    @RequestMapping("/getBill")
    public ElectricBillResponse getBillElectricity(@RequestBody ElectricBillRequest request)
    {
        return electricBillClient.getBill(request.idService(), request.referenceNumber());
    }
}
