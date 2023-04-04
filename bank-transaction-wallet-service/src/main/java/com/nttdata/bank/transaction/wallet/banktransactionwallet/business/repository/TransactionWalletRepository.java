package com.nttdata.bank.transaction.wallet.banktransactionwallet.business.repository;

import com.nttdata.bank.transaction.wallet.banktransactionwallet.business.entity.TransactionWallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionWalletRepository extends ReactiveMongoRepository <TransactionWallet,Integer>{
}
