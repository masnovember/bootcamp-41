package com.nttdata.bank.credit.bankcredit.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerMapper {

  @Bean
  public Mapper beanMapper() {
    return new DozerBeanMapper();
  }
}
