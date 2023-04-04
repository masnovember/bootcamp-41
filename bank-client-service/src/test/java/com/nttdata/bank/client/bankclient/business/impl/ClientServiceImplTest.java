package com.nttdata.bank.client.bankclient.business.impl;

import com.nttdata.bank.client.bankclient.business.entity.Client;
import com.nttdata.bank.client.bankclient.business.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class ClientServiceImplTest {
  @InjectMocks
  private ClientServiceImpl clientService;

  @Mock
  private ClientRepository clientRepository;

  private final Client client = new Client();

  @BeforeEach
  public void setUp() {
    BDDMockito.when(clientRepository.findAll()).thenReturn(Flux.just(client));

    BDDMockito.when(clientRepository.findById(ArgumentMatchers.anyInt()))
        .thenReturn(Mono.just(client));

    BDDMockito.when(clientRepository.delete(ArgumentMatchers.any(Client.class)))
        .thenReturn(Mono.empty());
  }

  @Test
  @DisplayName("find all return flux of clients when successfull")
  public void findAllReturnFluxOfClientsWhenSuccessfull() {
    StepVerifier.create(clientService.getAll())
        .expectSubscription()
        .expectNext(client)
        .verifyComplete();
  }

  @Test
  @DisplayName("Find by id return mono client when successfull")
  public void findByIdReturnMonoClientWhenSuccessfull() {
    StepVerifier.create(clientService.findById(1))
        .expectSubscription()
        .expectNext(client)
        .verifyComplete();
  }

  @Test
  @DisplayName("Delete return mono error when empty mono is returned")
  public void deleteReturnMonoErrorWhenEmptyMonoIsReturned() {
    BDDMockito.when(clientRepository.findById(ArgumentMatchers.anyInt()))
        .thenReturn(Mono.empty());

    StepVerifier.create(clientService.delete(1))
        .expectSubscription()
        .verifyComplete();
  }


}