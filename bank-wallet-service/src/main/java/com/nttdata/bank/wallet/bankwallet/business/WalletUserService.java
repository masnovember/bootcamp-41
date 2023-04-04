package com.nttdata.bank.wallet.bankwallet.business;

import com.nttdata.bank.wallet.bankwallet.business.entity.WalletUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletUserService {
  Flux<WalletUser> getAll();

  Mono<WalletUser> save(WalletUser walletUser);

  Mono<WalletUser> update(WalletUser walletUser);

  Mono<Void> delete(Integer walletUserId);

  Mono<WalletUser> findByMobileNumber(String mobileNumber);

  Mono<WalletUser> updBalanceKafka();

}
