package com.ms.account_service.mapper;

import com.ms.account_service.dto.AccountRequest;
import com.ms.account_service.dto.AccountResponse;
import com.ms.account_service.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "balance", source = "initialBalance")
    Account toEntity(AccountRequest accountRequest);
    
    AccountResponse toResponse(Account account);
}
