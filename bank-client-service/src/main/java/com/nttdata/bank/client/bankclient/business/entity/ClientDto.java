package com.nttdata.bank.client.bankclient.business.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientDto {

  private Integer clientId;
  private String clientName;
  private String clientType;
  private String clientDocument;
  private String clientProfile;
}
