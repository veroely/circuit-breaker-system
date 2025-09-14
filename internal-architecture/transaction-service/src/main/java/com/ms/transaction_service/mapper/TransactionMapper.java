package com.ms.transaction_service.mapper;

import com.ms.transaction_service.dto.TransactionRequest;
import com.ms.transaction_service.dto.TransactionResponse;
import com.ms.transaction_service.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(com.ms.transaction_service.model.Transaction.TransactionStatus.PENDING)")
    @Mapping(target = "transactionDate", expression = "java(java.time.LocalDateTime.now())")
    Transaction toEntity(TransactionRequest transactionRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "sourceAccountNumber", source = "sourceAccountNumber")
    @Mapping(target = "destinationAccountNumber", source = "destinationAccountNumber")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "transactionDate", source = "transactionDate")
    @Mapping(target = "description", source = "description")
    TransactionResponse toResponse(Transaction transaction);
}
