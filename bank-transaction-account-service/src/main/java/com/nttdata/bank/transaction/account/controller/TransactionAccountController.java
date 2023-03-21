package com.nttdata.bank.transaction.account.controller;

import com.nttdata.bank.transaction.account.banktransactionaccount.business.TransactionAccountService;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccount;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccountDto;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.WireTransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions/accounts")
public class TransactionAccountController {

  @Autowired
  private TransactionAccountService transactionAccountService;

  @GetMapping
  public Flux<TransactionAccount> getAll(){
    return transactionAccountService
        .getAll();
  }

  @GetMapping("/{accountId}")
  public Flux<TransactionAccount> getByAccountId(@PathVariable("accountId") Integer accountId) {
    return transactionAccountService
        .getByAccountId(accountId);
  }

  @PostMapping
  public Mono<TransactionAccount> save(@RequestBody TransactionAccountDto transactionAccountDto){
    return transactionAccountService
        .save(transactionAccountDto);
  }

  @PostMapping("/updTransaction")
  public Mono<TransactionAccount> update(@RequestBody TransactionAccountDto transactionAccountDto){
    return transactionAccountService.update(transactionAccountDto);
  }

  @PostMapping("/delete/{transactionAccountId}")
  public Mono<Void> deleteById(@PathVariable("transactionAccountId") Integer transactionAccountId){
    return transactionAccountService.delete(transactionAccountId);
  }

  @PostMapping("/transfer")
  public Mono<Void> transfer(@RequestBody WireTransferDto wireTransferDto) {
    return transactionAccountService.wireTransfer(wireTransferDto);
  }

}
