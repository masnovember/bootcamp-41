package com.nttdata.bank.client.bankclient.business.impl;

import com.nttdata.bank.client.bankclient.business.entity.AccountDto;
import com.nttdata.bank.client.bankclient.business.entity.CreditDto;
import com.nttdata.bank.client.bankclient.util.Parameters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.nttdata.bank.client.bankclient.business.repository.ClientRepository;

import java.time.Duration;


@Setter
@Getter
@NoArgsConstructor
@Component
@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ValidateClient {

  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private ExternalService externalService;
  private static long numberOfAccounts = 0;
  private static long accountBalance = 0;

  public Mono<Boolean> validateProfile(Integer clientId, String profile){
    return clientRepository
        .findById(clientId)
        .map(client -> {
          if (client.getClientType().equalsIgnoreCase(Parameters.CLIENT_TYPE_PERSONAL)) {
             return (profile.equalsIgnoreCase(Parameters.TYPE_PROFILE_PERSONAL) &&
                     validateBalanceAccount(clientId) &&
                     validateCreditCard(clientId));
          } else {
            return (profile.equalsIgnoreCase(Parameters.TYPE_PROFILE_BUSINESS)) &&
                validateCurrentAccount(clientId) &&
                validateCreditCard(clientId);
          }
        });
  }

  private Boolean validateBalanceAccount(Integer clientId) {
    Flux<AccountDto> accountDto = externalService.externalFindAccountByClientId(clientId);
    accountDto
        .filter(p-> p.getAccountBalance() < Parameters.ACCOUNT_AVERAGE_BALANCE)
        .count()
        .subscribe(count-> numberOfAccounts = count);
     return (numberOfAccounts == 0);
  }

  private Boolean validateCreditCard(Integer clientId) {
    Flux<CreditDto> creditDto = externalService.externalFindCreditByClientId(clientId);

    accountBalance = creditDto
      .filter(card-> Parameters.getCodeCreditCard().contains(card.getProductId()))
      .count()
          .block(Duration.ofSeconds(10L));
        //.subscribe(count-> accountBalance = count);

    return (accountBalance > 0);
  }

  private Boolean validateCurrentAccount(Integer clientId) {
    Flux<AccountDto> accountDto = externalService.externalFindAccountByClientId(clientId);
    accountDto
        .filter(a -> a.getProductId().equals(Parameters.CODE_CURRENT_ACCOUNT))
        .count()
        .subscribe(count->numberOfAccounts = count);
    return (numberOfAccounts > 0);
  }
}
