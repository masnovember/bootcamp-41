package com.nttdata.bank.transaction.account.banktransactionaccount.business.repository;


import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionAccountRepository extends ReactiveMongoRepository<TransactionAccount,Integer> {

  Flux<TransactionAccount> getByAccountId(Integer accountId);
}
