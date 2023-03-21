package com.nttdata.bank.client.controller;

import com.nttdata.bank.client.bankclient.business.ClientService;
import com.nttdata.bank.client.bankclient.business.entity.Client;
import com.nttdata.bank.client.bankclient.business.entity.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @GetMapping
  public Flux<Client> getAll() {
    return clientService
        .getAll();
  }

  @GetMapping("/{clientId}")
  public Mono<Client> getById(@PathVariable("clientId") Integer clientId) {
    return clientService
        .findById(clientId);
  }

  @PostMapping
  public Mono<Client> save(@Valid @RequestBody ClientDto clientDto) {
    return clientService
        .save(clientDto);
  }

  @PostMapping("/updClients")
  public Mono<Client> update(@Valid @RequestBody ClientDto clientDto) {
    return clientService
        .update(clientDto);
  }

  @PostMapping("/delete/{clientId}")
  public Mono<Void> deleteByClientId(@PathVariable("clientId") Integer clientId) {
    return clientService
        .delete(clientId);
  }

  @PostMapping("/updProfiles/{clientId}/{profile}")
  public Mono<Client> updateProfile(@PathVariable("clientId") Integer clientId,
                                    @PathVariable("profile") String profile) {
    return clientService
        .updateProfile(clientId,profile);
  }

}
