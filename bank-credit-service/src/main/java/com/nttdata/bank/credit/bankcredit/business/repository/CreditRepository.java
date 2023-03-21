package com.nttdata.bank.credit.bankcredit.business.repository;

import com.nttdata.bank.credit.bankcredit.business.entity.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditRepository extends ReactiveMongoRepository<Credit,Integer> {
}
