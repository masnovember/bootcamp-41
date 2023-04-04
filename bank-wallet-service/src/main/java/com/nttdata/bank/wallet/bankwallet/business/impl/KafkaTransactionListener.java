package com.nttdata.bank.wallet.bankwallet.business.impl;

import com.nttdata.bank.wallet.bankwallet.business.entity.TransactionWalletDto;
import com.nttdata.bank.wallet.bankwallet.business.entity.WalletUser;
import com.nttdata.bank.wallet.bankwallet.business.repository.WalletUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class KafkaTransactionListener {

  @Autowired
  private WalletUserRepository walletUserRepository;

  Logger logger = LoggerFactory.getLogger(KafkaTransactionListener.class);
  TransactionWalletDto transactionWalletDto = new TransactionWalletDto();

  @KafkaListener(topics = "transactionWalletTopic",
                groupId = "myGroup")
  public void listenTopic(String message) {

    String[] strArray = null;
    strArray = message.split(",");

    updBalance(strArray[0], -1 * Double.parseDouble(strArray[2])).subscribe();
    updBalance(strArray[1], Double.parseDouble(strArray[2])).subscribe();

    logger.info("Consuming Message {}", message);

  }

  public TransactionWalletDto getTransactionWallet() {
    logger.info("Consume",transactionWalletDto.getMobileNumberOrigin());
    return transactionWalletDto;
  }

  public Mono<WalletUser> updBalance(String mobileNumber, double amount) {
    return walletUserRepository
        .findAll()
        .filter(p-> p.getMobileNumber().equals(mobileNumber))
        .elementAt(0)
        .doOnNext(e->e.setWalletBalance(e.getWalletBalance() + amount))
        .flatMap(walletUserRepository::save)
        .switchIfEmpty(Mono.empty());
  }

}
