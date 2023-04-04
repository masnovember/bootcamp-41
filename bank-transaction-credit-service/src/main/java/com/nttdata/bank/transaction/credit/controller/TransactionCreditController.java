package com.nttdata.bank.transaction.credit.controller;

import com.nttdata.bank.transaction.credit.banktransactioncredit.business.TransactionCreditService;
import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.TransactionCredit;
import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.TransactionCreditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions/credits")
public class TransactionCreditController {

  @Autowired
  private TransactionCreditService transactionCreditService;

  @GetMapping
  public Flux<TransactionCredit> getAll(){
    return transactionCreditService
        .getAll();
  }

  @GetMapping("/{creditId}")
  public Flux<TransactionCredit> getByAccountId(@PathVariable("creditId") Integer creditId) {
    return transactionCreditService
        .getByCreditId(creditId);
  }

  @PostMapping
  public Mono<TransactionCredit> save(@RequestBody TransactionCreditDto transactionCreditDto){
    return transactionCreditService
        .save(transactionCreditDto);
  }

  @PostMapping("/updTransaction")
  public Mono<TransactionCredit> update(@RequestBody TransactionCreditDto transactionCreditDto){
    return transactionCreditService.update(transactionCreditDto);
  }

  @PostMapping("/delete/{transactionCreditId}")
  public Mono<TransactionCredit> deleteById(@PathVariable("transactionCreditId") Integer transactionCreditId){
    return transactionCreditService.delete(transactionCreditId);
  }
/*
  @PostMapping("/transfer")
  public Mono<Void> transfer(@RequestBody WireTransferDto wireTransferDto) {
    return transactionAccountService.wireTransfer(wireTransferDto);
  }
*/
  @GetMapping("/commission/{monthVar}")
  public Flux<TransactionCredit> getCommissionByMonth(@PathVariable("monthVar") Integer monthVar) {
    return transactionCreditService.getCommissionByMonth(monthVar);
  }

}
