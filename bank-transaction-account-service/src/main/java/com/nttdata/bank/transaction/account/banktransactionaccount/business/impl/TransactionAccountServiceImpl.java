package com.nttdata.bank.transaction.account.banktransactionaccount.business.impl;

import com.nttdata.bank.transaction.account.banktransactionaccount.business.TransactionAccountService;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.WireTransferDto;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.repository.TransactionAccountRepository;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccount;
import com.nttdata.bank.transaction.account.banktransactionaccount.business.entity.TransactionAccountDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionAccountServiceImpl implements TransactionAccountService {

  @Autowired
  private TransactionAccountRepository transactionAccountRepository;

  @Autowired
  private Mapper mapper;

  @Autowired
  private ValidateTransaction validateTransaction;

  @Override
  public Flux<TransactionAccount> getAll() {
    return transactionAccountRepository
        .findAll();
  }

  @Override
  public Mono<TransactionAccount> save(TransactionAccountDto transactionAccountDto) {
    return transactionAccountRepository.existsById(transactionAccountDto.getTransactionId())
        .flatMap(isExist->{
          if (!isExist) {
            return validateTransaction.updateBalance(transactionAccountDto);
          } else {
            return Mono.just(false);
          }
        })
        .flatMap(isTrue->{
          if (isTrue) {
            return transactionAccountRepository.save(mapper.map(transactionAccountDto,
                                                                TransactionAccount.class));
          }
          return Mono.empty();
        });
  }

  @Override
  public Mono<TransactionAccount> update(TransactionAccountDto transactionAccountDto) {
    return transactionAccountRepository.existsById(transactionAccountDto.getAccountId())
        .flatMap(isExist -> {
          if (isExist) {
            this.delete(transactionAccountDto.getTransactionId());
            return this.save(transactionAccountDto);
          }
          return Mono.empty();
        });
  }

  @Override
  public Mono<Void> delete(Integer transactionAccountId) {
    return transactionAccountRepository
        .findById(transactionAccountId)
        .flatMap(a-> validateTransaction.revertBalance(a))
        .flatMap(isTrue->{
          if (isTrue) {
            return transactionAccountRepository.deleteById(transactionAccountId);
          }
          return Mono.empty();
        });
  }

  @Override
  public Flux<TransactionAccount> getByAccountId(Integer accountId) {
    return transactionAccountRepository.findAll()
        .filter(p -> p.getAccountId().equals(accountId))
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<Void> wireTransfer(WireTransferDto wireTransferDto) {
    return validateTransaction.validWireTransfer(wireTransferDto)
        .flatMap(transfer->{

          transactionAccountRepository.save(mapper.map(transfer.getT1(), TransactionAccount.class));
          validateTransaction.updateBalance(transfer.getT1());

          transactionAccountRepository.save(mapper.map(transfer.getT2(), TransactionAccount.class));
          validateTransaction.updateBalance(transfer.getT2());

          return Mono.empty();
        });
  }


}
