package com.nttdata.bank.wallet.controller;

import com.nttdata.bank.wallet.bankwallet.business.WalletUserService;
import com.nttdata.bank.wallet.bankwallet.business.entity.WalletUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  @Autowired
  private WalletUserService walletUserService;

  @GetMapping
  public Flux<WalletUser> getAll() {
    return walletUserService
        .getAll();
  }

  @GetMapping("/{mobileNumber}")
  public Mono<WalletUser> getByMobileNumber(@PathVariable("mobileNumber") String mobileNumber) {
    return walletUserService
        .findByMobileNumber(mobileNumber);
  }

  @PostMapping
  public Mono<WalletUser> save(@Valid @RequestBody WalletUser walletUser) {
    return walletUserService
        .save(walletUser);
  }

  @PostMapping("/updWallets")
  public Mono<WalletUser> update(@Valid @RequestBody WalletUser walletUser) {
    return walletUserService
        .update(walletUser);
  }

  @PostMapping("/delete/{walletUserId}")
  public Mono<Void> deleteByWalletUserId(@PathVariable("walletUserId") Integer walletUserId) {
    return walletUserService
        .delete(walletUserId);
  }

  @PostMapping("updBalanceKafka")
  public Mono<WalletUser> updBalanceKafka () {
    return walletUserService
        .updBalanceKafka();
  }

}
