package com.ms.payment_management_service.infraestructure.adapter.rest;

import com.ms.payment_management_service.application.port.input.PaymentService;
import com.ms.payment_management_service.application.service.PaymentServiceImpl;
import com.ms.payment_management_service.domain.ElectricBillRequest;
import com.ms.payment_management_service.domain.ElectricBillResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping
    @RequestMapping("/getBill")
    public ElectricBillResponse getBillElectricity(@RequestBody ElectricBillRequest request)
    {
        return paymentService.getPaymentDetail(request);
    }
}
