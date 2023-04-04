package com.nttdata.bank.transaction.wallet.banktransactionwallet.business.impl;

import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.entity.TransactionWallet;
import com.nttdata.bank.transaction.wallet.banktransactionwallet.util.Parameters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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

  public Mono<Tuple2<TransactionWallet, TransactionWallet>> validTransaction(TransactionWallet transactionWallet) {

    return Mono.zip( Mono.just(transactionWallet), Mono.just(new TransactionWallet()),
            (origin, destination)->{
              origin.setTransactionType(Parameters.OPERATION_WITHDRAWAL);

              destination.setTransactionId(origin.getTransactionId()+1);
              destination.setTransactionType(Parameters.OPERATION_DEPOSIT);
              destination.setMobileNumberOrigin(origin.getMobileNumberDestination());
              destination.setMobileNumberDestination(origin.getMobileNumberOrigin());
              destination.setTransactionAmount(origin.getTransactionAmount());
              destination.setTransactionDate(origin.getTransactionDate());

              return Tuples.of(origin, destination);

            })
        .onErrorResume(ex-> Mono.error(new Exception("Error")));

  }

}
