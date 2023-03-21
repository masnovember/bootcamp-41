package com.nttdata.bank.account.bankaccount.business.impl;


import com.nttdata.bank.account.bankaccount.business.entity.ClientDto;
import com.nttdata.bank.account.bankaccount.util.Parameters;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@NoArgsConstructor
@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExternalService {

  @Autowired
  public WebClient.Builder webClientBuilder;

    public Mono<ClientDto> externalFindByClientId(Integer clientId) {
      return webClientBuilder.baseUrl(Parameters.URL_MS_CLIENTS)
        .build()
        .get()
        .uri("/clients/{clientId}", clientId)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(ClientDto.class);
  }

  }

