package com.ms.electric_bill_service.infrastructure.repository;

import com.ms.electric_bill_service.application.port.ouput.ElectricBillRepository;
import com.ms.electric_bill_service.domain.ElectricBill;
import com.ms.electric_bill_service.domain.exception.BillNotFoundException;
import com.ms.electric_bill_service.domain.exception.ElectricBillServiceException;
import com.ms.electric_bill_service.infrastructure.adapter.feign.ElectricityProviderClient;
import com.ms.electric_bill_service.infrastructure.adapter.mapper.ElectricMapper;
import com.ms.electric_bill_service.dto.ElectricBillResponse;
import org.springframework.stereotype.Repository;
import feign.FeignException;

@Repository
public class ElectricBillRepositoryImpl implements ElectricBillRepository {
    private final ElectricityProviderClient electricityProviderClient;
    private final ElectricMapper mapper;

    public ElectricBillRepositoryImpl(ElectricityProviderClient electricityProviderClient, ElectricMapper mapper) {
        this.electricityProviderClient = electricityProviderClient;
        this.mapper = mapper;
    }

    @Override
    public ElectricBill findByReference(String reference) {
        try {
            ElectricBillResponse billResponse = electricityProviderClient.getBill(reference);
            return mapper.toElectricBill(billResponse);
        } catch (FeignException.NotFound e) {
            throw new BillNotFoundException("Bill not found for reference: " + reference, e);
        } catch (FeignException e) {
            throw new ElectricBillServiceException("Error communicating with electricity provider", e);
        } catch (Exception e) {
            throw new ElectricBillServiceException("Unexpected error while fetching bill", e);
        }
    }
}
