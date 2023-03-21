package com.nttdata.bank.client.bankclient.business.repository;

import com.nttdata.bank.client.bankclient.business.entity.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Integer> {

}
