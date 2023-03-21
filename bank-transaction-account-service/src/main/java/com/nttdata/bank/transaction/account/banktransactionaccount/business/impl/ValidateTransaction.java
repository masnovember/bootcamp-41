package com.nttdata.bank.transaction.account.banktransactionaccount.business.impl;

import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.AccountDto;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccount;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccountDto;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.WireTransferDto;
import com.nttdata.bank.transaction.account.banktransactionaccount.util.Parameters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Setter
@Getter
@NoArgsConstructor
@Component
@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ValidateTransaction {
  @Autowired
  private ExternalService externalService;

  public Mono<Boolean> updateBalance(TransactionAccountDto transactionAccountDto) {

    Float amount = transactionAccountDto.getTransactionAmount();
    if (Parameters.getRemainingOperations().contains(transactionAccountDto.getTransactionType())) {
      amount = amount * -1;
    }

    Mono<AccountDto> accountDtoMono = externalService.
                                      externalUpdateBalance(transactionAccountDto.getAccountId(),
                                      amount);
                      accountDtoMono.subscribe();

    return Mono.just(true);
  }

  public Mono<Boolean> revertBalance(TransactionAccount transactionAccountDto) {

    Float amount = transactionAccountDto.getTransactionAmount();
    if (!(Parameters.getRemainingOperations().contains(transactionAccountDto.getTransactionType()))) {
      amount = amount * -1;
    }

    Mono<AccountDto> accountDtoMono = externalService.
        externalUpdateBalance(transactionAccountDto.getAccountId(),
            amount);
    accountDtoMono.subscribe();

    return Mono.just(true);
  }

  public Mono<Tuple2<TransactionAccountDto, TransactionAccountDto>>  validWireTransfer(WireTransferDto wireTransferDto) {

    Mono<AccountDto> accountDtoOrigin = externalService
        .externalFindAccountByNumber(wireTransferDto.getAccountNumberOrigin())
        .switchIfEmpty(Mono.just(new AccountDto()));

    Mono<AccountDto> accountDtoDestination = externalService
        .externalFindAccountByNumber(wireTransferDto.getAccountNumberDestination())
        .switchIfEmpty(Mono.just(new AccountDto()));

    return Mono.zip(accountDtoOrigin, accountDtoDestination,
        (origin, destination)->{

          if (origin.getAccountNumber().isEmpty()){
            log.info("Error Cuenta Origen no encontrada");
            throw new IllegalArgumentException("Cuenta Origen no encontrada");
          }

          if (destination.getAccountNumber().isEmpty()){
            log.info("Error Cuenta Destino no encontrada");
            throw new IllegalArgumentException("Cuenta Destino no encontrada");
          }

          if ( origin.getAccountBalance() >= wireTransferDto.getTransactionAmount()){
              log.info("Error Saldo insuficiente");
              throw new IllegalArgumentException("Saldo Insuficiente");
          }

          TransactionAccountDto accountOrigin = new TransactionAccountDto();
          accountOrigin.setTransactionId(wireTransferDto.getTransactionId());
          accountOrigin.setTransactionType(Parameters.OPERATION_WITHDRAWAL_TRANSFER);
          accountOrigin.setAccountId(origin.getAccountId());
          accountOrigin.setTransactionAmount(wireTransferDto.getTransactionAmount());
          accountOrigin.setTransactionDate(wireTransferDto.getTransactionDate());

          TransactionAccountDto accountDestination = new TransactionAccountDto();
          accountDestination.setTransactionId(wireTransferDto.getTransactionId());
          accountDestination.setTransactionType(Parameters.OPERATION_DEPOSIT_TRANSFER);
          accountDestination.setAccountId(destination.getAccountId());
          accountDestination.setTransactionAmount(wireTransferDto.getTransactionAmount());
          accountDestination.setTransactionDate(wireTransferDto.getTransactionDate());

          return Tuples.of(accountOrigin, accountDestination);

      })
        .onErrorResume(ex-> Mono.error(new Exception("Error")));

  }

}