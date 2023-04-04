package com.nttdata.bank.transaction.credit.banktransactioncredit.business;

import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.TransactionCredit;
import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.TransactionCreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionCreditService {
  Flux<TransactionCredit> getAll();

  Mono<TransactionCredit> save(TransactionCreditDto transactionCreditDto);

  Mono<TransactionCredit> update(TransactionCreditDto transactionCreditDto);

  Mono<TransactionCredit> delete(Integer transactionCreditId);

  Flux<TransactionCredit> getByCreditId(Integer creditId);

  //Mono<Void> wireTransfer (WireTransferDto wireTransferDto);

  Flux<TransactionCredit> getCommissionByMonth(Integer monthVar);

}
