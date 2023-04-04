package com.nttdata.bank.credit.bankcredit.business;

import com.nttdata.bank.credit.bankcredit.business.entity.Credit;
import com.nttdata.bank.credit.bankcredit.business.entity.CreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

  Flux<Credit> getAll();

  Mono<Credit> save(CreditDto creditDto);

  Mono<Credit> update(CreditDto creditDto);

  Mono<Void> delete(Integer creditId);

  Mono<Credit> findById(Integer creditId);

  Flux<Credit> findByClientId(Integer clientId);

  Mono<Credit> findByCreditNumber(String creditNumber);

  Flux<Object> getBalanceByClientId (Integer clientId);

  Mono<Credit> UpdBalance(Integer creditId, Float amount);

  Mono<Credit> associateAccounts(String creditNumber, String accountNumber);

}
