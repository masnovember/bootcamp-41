package com.nttdata.bank.account.bankaccount.business.impl;

import com.nttdata.bank.account.bankaccount.business.entity.AccountDto;
import com.nttdata.bank.account.bankaccount.business.entity.ClientDto;
import com.nttdata.bank.account.bankaccount.business.repository.AccountRepository;
import com.nttdata.bank.account.bankaccount.util.Parameters;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Setter
@Getter
@NoArgsConstructor
@Component
@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ValidateClient {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ExternalService externalService;
    private static long numberOfAccounts = 0;

    public Mono<Boolean> validateTypeClient(AccountDto accountDto){

      Mono<ClientDto> clientDto = externalService
                                  .externalFindByClientId(accountDto.getClientId());
      return clientDto
          .map(cliente->{
            if (cliente.getClientType().equalsIgnoreCase(Parameters.CLIENT_TYPE_PERSONAL)){
              return validateNumberOfAccountsPersonal(cliente.getClientId());
            } else {
              return (Parameters.CODE_CURRENT_ACCOUNT.equals(accountDto.getProductId()));
            }
          });
    }

    private boolean validateNumberOfAccountsPersonal(Integer clientId){
      accountRepository.findAll()
          .filter(p -> p.getClientId().equals(clientId))
          .count()
          .subscribe(count-> numberOfAccounts = count);
      return (numberOfAccounts == 0 );
    }

}
