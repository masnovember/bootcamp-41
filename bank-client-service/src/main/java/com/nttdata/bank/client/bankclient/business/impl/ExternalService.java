package com.nttdata.bank.client.bankclient.business.impl;

import com.nttdata.bank.client.bankclient.business.entity.AccountDto;
import com.nttdata.bank.client.bankclient.business.entity.CreditDto;
import com.nttdata.bank.client.bankclient.util.Parameters;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@NoArgsConstructor
@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExternalService {
  @Autowired
  public WebClient.Builder webClientBuilder;
  public Flux<AccountDto> externalFindAccountByClientId(Integer clientId) {
    return webClientBuilder.baseUrl(Parameters.URL_MS_ACCOUNT)
        .build()
        .get()
        .uri("/accounts/byClient/{clientId}", clientId)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(AccountDto.class);
  }

  public Flux<CreditDto> externalFindCreditByClientId(Integer clientId) {
    return webClientBuilder.baseUrl(Parameters.URL_MS_CREDIT)
        .build()
        .get()
        .uri("/credits/byClient/{clientId}", clientId)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(CreditDto.class);
  }
}
