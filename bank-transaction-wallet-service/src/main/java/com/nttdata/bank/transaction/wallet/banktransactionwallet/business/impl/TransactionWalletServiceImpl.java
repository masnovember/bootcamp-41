package com.nttdata.bank.transaction.wallet.banktransactionwallet.business.impl;

import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.TransactionWalletService;
import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.entity.TransactionWallet;
import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.repository.TransactionWalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TransactionWalletServiceImpl implements TransactionWalletService {
  @Autowired
  private TransactionWalletRepository transactionWalletRepository;
  @Autowired
  private ValidateTransaction validateTransaction;

  @Autowired
  private KafkaStringProducer kafkaStringProducer;

  @Override
  public Flux<TransactionWallet> getAll() {
    return transactionWalletRepository
        .findAll();
  }
  @Override
  public Mono<TransactionWallet> save(TransactionWallet transactionWallet) {
    return validateTransaction.validTransaction(transactionWallet)
        .flatMap(transaction -> {

          String message = transaction.getT1().getMobileNumberOrigin() +","+
                           transaction.getT1().getMobileNumberDestination() +","+
                           transaction.getT1().getTransactionAmount();

          kafkaStringProducer.sendMessage(message);

          transactionWalletRepository.save(transaction.getT1()).subscribe();
          transactionWalletRepository.save(transaction.getT2()).subscribe();
          return Mono.just(transactionWallet);
        });
  }

}
