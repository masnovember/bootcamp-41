package com.nttdata.bank.credit.bankcredit.business.impl;

import com.nttdata.bank.credit.bankcredit.business.entity.*;
import com.nttdata.bank.credit.bankcredit.business.repository.CreditRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Component
@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ValidateRegister {
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private ExternalService externalService;

    public Mono<Boolean> mappingCredit(CreditDto creditDto) {

      Mono<Client> clientDto = externalService
          .externalFindByClientId(creditDto.getClient().getClientId());

      Mono<Product> productDto = externalService
          .externalFindByProductId(creditDto.getProduct().getProductId());

    return Mono.zip(clientDto,productDto,(client, product) -> {
              creditDto.setClient(client);
              creditDto.setProduct(product);
              return Mono.empty();
            })
        .flatMap(p-> Mono.just(true));
    }

    public Mono<Credit> associateAccount(Credit credit, String accountNumber){

      return externalService
            .externalFindByAccountNumber(accountNumber)
            .map(account -> {
                List<Account> accountList = credit.getAccountList();
                accountList.add(account);
              credit.setAccountList(accountList);
                return credit;
            });

    }

}
