package com.nttdata.bank.client.bankclient.business.impl;

import com.nttdata.bank.client.bankclient.business.entity.AccountDto;
import com.nttdata.bank.client.bankclient.business.entity.CreditDto;
import com.nttdata.bank.client.bankclient.util.ExternalService;
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
  private static float accountBalance = 0;

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
    accountDto.subscribe(accountDto1 -> accountBalance = accountDto1.getAccountBalance());
    return (accountBalance >= Parameters.ACCOUNT_AVERAGE_BALANCE);
  }

  private Boolean validateCreditCard(Integer clientId) {
    Flux<CreditDto> creditDto = externalService.externalFindCreditByClientId(clientId);
    creditDto
        .filter(card-> Parameters.getCodeCreditCard().contains(card.getProductId()))
        .count()
        .subscribe(count->numberOfAccounts = count);
    return (numberOfAccounts > 0);
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
