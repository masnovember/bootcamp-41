package com.nttdata.bank.credit.bankcredit.business.impl;

import com.nttdata.bank.credit.bankcredit.business.CreditService;
import com.nttdata.bank.credit.bankcredit.business.entity.Credit;
import com.nttdata.bank.credit.bankcredit.business.entity.CreditDto;
import com.nttdata.bank.credit.bankcredit.business.repository.CreditRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreditServiceImpl implements CreditService {

  @Autowired
  private CreditRepository creditRepository;

  @Autowired
  private Mapper mapper;

  @Override
  public Flux<Credit> getAll() {
    return creditRepository.findAll();
  }

  @Override
  public Mono<Credit> save(CreditDto creditDto) {
    return creditRepository.existsById(creditDto.getCreditId())
        .flatMap((isExist-> {
          if (!isExist) {
            return creditRepository.save(mapper.map(creditDto, Credit.class));
          } else {
            return Mono.empty();
          }
        }));
  }

  @Override
  public Mono<Credit> update(CreditDto creditDto) {
    return creditRepository.findById(creditDto.getCreditId())
        .map(c->mapper.map(creditDto,Credit.class))
        .flatMap(creditRepository::save)
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<Void> delete(Integer creditId) {
    return creditRepository.findById(creditId)
        .flatMap(p->creditRepository.deleteById(creditId)
            .switchIfEmpty(Mono.empty()));
  }

  @Override
  public Mono<Credit> findById(Integer creditId) {
    return this.creditRepository.findById(creditId);
  }

  @Override
  public Flux<Credit> findByClientId(Integer clientId) {
    return creditRepository.findAll()
        .filter(p->p.getClientId() == clientId);
  }

  @Override
  public Flux<Object> getBalanceByClientId(Integer clientId) {
    return this.creditRepository.findAll()
        .filter(p -> p.getClientId().equals(clientId))
        .flatMap(x -> {
          Map<String, String> map = new HashMap<>();
          map.put("creditNumber", x.getCreditNumber());
          map.put("creditLine", String.valueOf(x.getCreditLine()));
          return Mono.just(map);
        });
  }
}