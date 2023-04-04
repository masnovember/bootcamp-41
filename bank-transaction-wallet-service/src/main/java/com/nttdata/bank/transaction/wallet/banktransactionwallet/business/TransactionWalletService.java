package com.nttdata.bank.transaction.wallet.banktransactionwallet.business;

import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.entity.TransactionWallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionWalletService {

  Flux<TransactionWallet> getAll();

  Mono<TransactionWallet> save(TransactionWallet transactionWallet);

}
