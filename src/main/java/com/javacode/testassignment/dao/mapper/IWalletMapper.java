package com.javacode.testassignment.dao.mapper;

import com.javacode.testassignment.dao.dto.WalletDTO;
import com.javacode.testassignment.dao.model.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IWalletMapper {

    WalletDTO toDTO(Wallet friend);

    Wallet toEntity(WalletDTO friendDTO);
}
