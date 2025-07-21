package com.pingpay.mapper;

import com.pingpay.dto.WalletRequestDTO;
import com.pingpay.dto.WalletResponseDTO;
import com.pingpay.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponseDTO mapToResponse(Wallet wallet);

    @Mapping(target = "password", source = "password")
    Wallet mapToWallet(WalletRequestDTO walletRequestDTO);

    List<WalletResponseDTO> mapToResponseList(List<Wallet> wallets);
}
