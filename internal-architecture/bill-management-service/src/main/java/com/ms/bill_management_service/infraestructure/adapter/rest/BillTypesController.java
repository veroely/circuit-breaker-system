package com.ms.bill_management_service.infraestructure.adapter.rest;

import com.ms.bill_management_service.domain.BillTypeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bill-types")
public class BillTypesController {
    @GetMapping
    public Object getBillTypes() {
        return List.of(
                new BillTypeResponse(
                        "electricity",
                        "Luz Eléctrica",
                        "https://example.com/icons/electricity.png"
                ),
                new BillTypeResponse(
                        "water",
                        "Agua Potable",
                        "https://example.com/icons/water.png"
                ),
                new BillTypeResponse(
                        "phone",
                        "Telefonía",
                        "https://example.com/icons/phone.png"
                )
        );
    }
}
