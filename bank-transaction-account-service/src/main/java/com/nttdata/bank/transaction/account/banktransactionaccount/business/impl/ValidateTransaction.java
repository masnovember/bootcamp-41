package com.nttdata.bank.transaction.account.banktransactionaccount.business.impl;

import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.Account;
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

    return externalService
           .externalFindAccountByNumber(transactionAccountDto
           .getAccount().getAccountNumber())
           .flatMap( account -> {
              transactionAccountDto.setAccount(account);

              float amount = transactionAccountDto.getTransactionAmount();
              if (Parameters.getRemainingOperations().contains(transactionAccountDto
                                                               .getTransactionType())) {
               amount = amount * -1;
             }

              externalService.externalUpdateBalance(transactionAccountDto
                                                    .getAccount().getAccountId(), amount).subscribe();

              return Mono.just(true);
           });
  }

  public Mono<TransactionAccount> revertBalance(TransactionAccount transactionAccount) {

    TransactionAccount transactionAccountMono = transactionAccount;

    float amount = transactionAccount.getTransactionAmount();
    if (!(Parameters.getRemainingOperations().contains(transactionAccount.getTransactionType()))) {
      amount = amount * -1;
    }

    externalService.externalUpdateBalance(transactionAccount.getAccount().getAccountId(), amount)
                    .subscribe();

    transactionAccountMono.setTransactionId(0);
    transactionAccountMono.setTransactionType(Parameters.OPERATION_EXTORT);
    transactionAccountMono.setTransactionAmount(amount);

    return Mono.just(transactionAccountMono);
  }

  public Mono<Tuple2<TransactionAccountDto, TransactionAccountDto>>  validWireTransfer(WireTransferDto wireTransferDto) {

    Mono<Account> accountDtoOrigin = externalService
        .externalFindAccountByNumber(wireTransferDto.getAccountNumberOrigin())
        .switchIfEmpty(Mono.just(new Account()));

    Mono<Account> accountDtoDestination = externalService
        .externalFindAccountByNumber(wireTransferDto.getAccountNumberDestination())
        .switchIfEmpty(Mono.just(new Account()));

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
          accountOrigin.setTransactionAmount(wireTransferDto.getTransactionAmount());
          accountOrigin.setTransactionDate(wireTransferDto.getTransactionDate());
          accountOrigin.setAccount(origin);

          TransactionAccountDto accountDestination = new TransactionAccountDto();
          accountDestination.setTransactionId(wireTransferDto.getTransactionId());
          accountDestination.setTransactionType(Parameters.OPERATION_DEPOSIT_TRANSFER);
          accountDestination.setTransactionAmount(wireTransferDto.getTransactionAmount());
          accountDestination.setTransactionDate(wireTransferDto.getTransactionDate());
          accountDestination.setAccount(destination);

          return Tuples.of(accountOrigin, accountDestination);

      })
        .onErrorResume(ex-> Mono.error(new Exception("Error")));

  }

}