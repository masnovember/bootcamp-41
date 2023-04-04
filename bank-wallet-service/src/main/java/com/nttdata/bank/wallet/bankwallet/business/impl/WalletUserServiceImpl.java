package com.nttdata.bank.wallet.bankwallet.business.impl;

import com.nttdata.bank.wallet.bankwallet.business.WalletUserService;
import com.nttdata.bank.wallet.bankwallet.business.entity.WalletUser;
import com.nttdata.bank.wallet.bankwallet.business.repository.WalletUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class WalletUserServiceImpl implements WalletUserService {

  @Autowired
  private WalletUserRepository walletUserRepository;

  @Autowired
  private KafkaTransactionListener kafkaTransactionListener;

  @Override
  public Flux<WalletUser> getAll() {
    return walletUserRepository
        .findAll()
        .switchIfEmpty(Flux.empty());
  }

  @Override
  public Mono<WalletUser> save(WalletUser walletUser) {
    return walletUserRepository
        .existsById(walletUser.getWalletUserId())
        .flatMap((isExist -> {
          if (!isExist) {
            return walletUserRepository.save(walletUser);
          } else {
            return Mono.empty();
          }
        }));
  }

  @Override
  public Mono<WalletUser> update(WalletUser walletUser) {
    return walletUserRepository
        .existsById(walletUser.getWalletUserId())
        .flatMap((isExist -> {
          if (isExist) {
            return walletUserRepository.save(walletUser);
          } else {
            return Mono.empty();
          }
        }));
  }

  @Override
  public Mono<Void> delete(Integer walletUserId) {
    return walletUserRepository
        .findById(walletUserId)
        .flatMap(p -> walletUserRepository.deleteById(walletUserId))
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<WalletUser> findByMobileNumber(String mobileNumber) {
    return walletUserRepository
        .findAll()
        .filter(p-> p.getMobileNumber().equals(mobileNumber))
        .elementAt(0);
  }

  @Override
  public Mono<WalletUser> updBalanceKafka() {
    Mono.just(kafkaTransactionListener.getTransactionWallet())
        .timeout(Duration.ofSeconds(5))
        .doOnSuccess(transaction->{
          log.info(transaction.getMobileNumberOrigin());
          log.info(transaction.getMobileNumberDestination());
          log.info(String.valueOf(transaction.getTransactionAmount()));
        })
        .doOnError(throwable -> log.info(throwable.getLocalizedMessage()));
    return Mono.empty();
  }

}
