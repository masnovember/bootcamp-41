package com.nttdata.bank.account.bankaccount.business.repository;

import com.nttdata.bank.account.bankaccount.business.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, Integer> {

  Flux<Account> findByClientId(Integer clientId);

  Mono<Account> findByAccountNumber(String accountNumber);

  Flux<Object> getBalanceByClientId(Integer clientId);

  Flux<Account> getAccountByProductId(String productId, String dateIni, String dateEnd);

}
