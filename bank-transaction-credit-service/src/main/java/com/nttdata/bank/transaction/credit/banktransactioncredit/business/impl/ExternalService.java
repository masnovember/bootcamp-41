package com.nttdata.bank.transaction.credit.banktransactioncredit.business.impl;

import com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity.Credit;
import com.nttdata.bank.transaction.credit.banktransactioncredit.util.Parameters;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@NoArgsConstructor
@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExternalService {
  @Autowired
  public WebClient.Builder webClientBuilder;

  public Mono<Credit> externalUpdateBalance(Integer creditId, Float amount) {
    return webClientBuilder.baseUrl(Parameters.URL_MS_CREDIT)
        .build()
        .post()
        .uri(uriBuilder -> uriBuilder
            .path("/credits/updBalance/{creditId}/{amount}")
            .build(creditId, amount))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Credit.class);
  }

  public Mono<Credit> externalFindCreditByNumber(String creditNumber) {
    return webClientBuilder.baseUrl(Parameters.URL_MS_CREDIT)
        .build()
        .post()
        .uri(uriBuilder -> uriBuilder
            .path("/credits/byCreditNumber/{creditNumber}")
            .build(creditNumber))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Credit.class);
  }
}
