package com.nttdata.bank.client.bankclient.business.impl;

import com.nttdata.bank.client.bankclient.business.ClientService;
import com.nttdata.bank.client.bankclient.business.entity.Client;
import com.nttdata.bank.client.bankclient.business.entity.ClientDto;
import com.nttdata.bank.client.bankclient.business.repository.ClientRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private Mapper mapper;

  @Autowired
  private ValidateClient validateClient;

  @Override
  public Flux<Client> getAll() {
    return clientRepository
        .findAll()
        .switchIfEmpty(Flux.empty());
  }

  @Override
  public Mono<Client> save(ClientDto clientDto) {
    return clientRepository
        .existsById(clientDto.getClientId())
        .flatMap((isExist -> {
          if (!isExist) {
            return clientRepository.save(mapper.map(clientDto, Client.class));
          } else {
            return Mono.empty();
          }
        }));
  }

  @Override
  public Mono<Client> update(ClientDto clientDto) {
    return clientRepository
        .existsById(clientDto.getClientId())
        .flatMap((isExist -> {
          if (isExist) {
            return clientRepository.save(mapper.map(clientDto, Client.class));
          } else {
            return Mono.empty();
          }
        }));
  }

  @Override
  public Mono<Void> delete(Integer clientId) {
    return clientRepository
        .findById(clientId)
        .flatMap(p -> clientRepository.deleteById(clientId))
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<Client> findById(Integer clientId) {
    return clientRepository.
        findById(clientId);
  }

  @Override
  public Mono<Client> updateProfile(Integer clientId, String profile) {
    return clientRepository
        .existsById(clientId)
        .flatMap(isExist -> {
          if (isExist) {
            return validateClient.validateProfile(clientId, profile);
          }
          return Mono.just(false);
        })
        .flatMap(isTrue->{
          if (isTrue) {
            Mono<Client> client = clientRepository.findById(clientId);
            client
                .flatMap(clientProfile -> {
                  clientProfile.setClientProfile(profile);
                  return clientRepository.save(clientProfile);
                });
          }
          return Mono.empty();
        });
  }

}
