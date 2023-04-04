package com.nttdata.bank.transaction.account.banktransactionaccount.business;

import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccount;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccountDto;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.WireTransferDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionAccountService {

  Flux<TransactionAccount> getAll();

  Mono<TransactionAccount> save(TransactionAccountDto transactionAccountDto);

  Mono<TransactionAccount> update(TransactionAccountDto transactionAccountDto);

  Mono<TransactionAccount> delete(Integer transactionAccountId);

  Flux<TransactionAccount> getByAccountId(Integer accountId);

  Mono<Void> wireTransfer (WireTransferDto wireTransferDto);

  Flux<TransactionAccount> getCommissionByMonth(Integer monthVar);

}
