package com.nttdata.bank.transaction.wallet.controller;

import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.TransactionWalletService;
import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.entity.TransactionWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/transactions/wallet")
public class TransactionWalletController {

  @Autowired
  private TransactionWalletService transactionWalletService;

  @GetMapping
  public Flux<TransactionWallet> getAll(){
    return transactionWalletService
        .getAll();
  }

  @PostMapping
  public Mono<TransactionWallet> save(@Valid @RequestBody TransactionWallet transactionWallet){
    return transactionWalletService
        .save(transactionWallet);
  }
}
