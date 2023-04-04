package com.nttdata.bank.transaction.credit.banktransactioncredit.business.repository;

import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.TransactionCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionCreditRepository extends ReactiveMongoRepository<TransactionCredit,Integer> {
}
