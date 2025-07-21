package com.pingpay.mapper;

import com.pingpay.dto.TransactionRequestDTO;
import com.pingpay.dto.TransactionsResponseDTO;
import com.pingpay.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction mapToEntity(TransactionRequestDTO transactionRequestDTO);

    TransactionsResponseDTO mapToResponse(Transaction transaction);
}
