package com.ms.bill_management_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BillTypeResponse {
    private String serviceType;
    private String displayName;
    private String iconUrl;
}
