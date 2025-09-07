package com.ms.electric_bill_service.infrastructure.adapter.mapper;

import com.ms.electric_bill_service.domain.ElectricBill;
import com.ms.electric_bill_service.dto.ElectricBillResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ElectricMapper {

    @Mapping(target = "dueDate", source = "dueDate", dateFormat = "yyyy-MM-dd")
    ElectricBill toElectricBill(ElectricBillResponse billResponse);

    @Mapping(target = "dueDate", source = "dueDate", dateFormat = "yyyy-MM-dd")
    ElectricBillResponse toElectricBillResponse(ElectricBill bill);
}
