package com.nttdata.bank.wallet.bankwallet.business.repository;

import com.nttdata.bank.wallet.bankwallet.business.entity.WalletUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WalletUserRepository extends ReactiveMongoRepository<WalletUser, Integer> {
}
