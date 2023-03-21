package com.nttdata.bank.client.bankclient.business;

import com.nttdata.bank.client.bankclient.business.entity.Client;
import com.nttdata.bank.client.bankclient.business.entity.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

  Flux<Client> getAll();

  Mono<Client> save(ClientDto clientDto);

  Mono<Client> update(ClientDto clientDto);

  Mono<Void> delete(Integer clientId);

  Mono<Client> findById(Integer clientId);

  Mono<Client> updateProfile(Integer clientId, String profile);

}
