package com.nttdata.bank.transaction.credit.banktransactioncredit.business.impl;

import com.nttdata.bank.transaction.credit.banktransactioncredit.business.TransactionCreditService;
import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.TransactionCredit;
import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.TransactionCreditDto;
import com.nttdata.bank.transaction.credit.banktransactioncredit.business.repository.TransactionCreditRepository;
import com.nttdata.bank.transaction.credit.banktransactioncredit.util.Parameters;
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
public class TransactionCreditServiceImpl implements TransactionCreditService {

  @Autowired
  private TransactionCreditRepository transactionCreditRepository;

  @Autowired
  private Mapper mapper;

  @Autowired
  private ValidateTransaction validateTransaction;

  @Override
  public Flux<TransactionCredit> getAll() {
    return transactionCreditRepository
        .findAll();
  }

  @Override
  public Mono<TransactionCredit> save(TransactionCreditDto transactionCreditDto) {
    return transactionCreditRepository.existsById(transactionCreditDto.getTransactionId())
        .flatMap(isExist->{
          if (!isExist) {
            return validateTransaction.updateBalance(transactionCreditDto);
          } else {
            return Mono.just(false);
          }
        })
        .flatMap(isTrue->{
          if (isTrue) {
            return transactionCreditRepository.save(mapper.map(transactionCreditDto,
                TransactionCredit.class));
          }
          return Mono.empty();
        });
  }

  @Override
  public Mono<TransactionCredit> update(TransactionCreditDto transactionCreditDto) {
    return transactionCreditRepository.existsById(transactionCreditDto.getCredit().getCreditId())
        .flatMap(isExist -> {
          if (isExist) {
            delete(transactionCreditDto.getTransactionId());
            return save(transactionCreditDto);
          }
          return Mono.empty();
        });
  }

  @Override
  public Mono<TransactionCredit> delete(Integer transactionCreditId) {
    return transactionCreditRepository
        .findById(transactionCreditId)
        .flatMap(a-> validateTransaction.revertBalance(a))
        .flatMap(p-> transactionCreditRepository.save(p));
  }

  @Override
  public Flux<TransactionCredit> getByCreditId(Integer creditId) {
    return transactionCreditRepository.findAll()
        .filter(p -> p.getCredit().getCreditId().equals(creditId))
        .switchIfEmpty(Mono.empty());
  }
/*
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
*/
  @Override
  public Flux<TransactionCredit> getCommissionByMonth(Integer monthVar) {
    return transactionCreditRepository
        .findAll()
        .filter(transactionCredit ->
            Integer.parseInt(transactionCredit
                .getTransactionDate()
                .toString()
                .substring(4,5)) == monthVar)
        .filter(transactionCredit -> transactionCredit.getTransactionType().equals(Parameters.OPERATION_COMMISSION_EXCESS))
        .switchIfEmpty(Flux.empty());
  }

}
