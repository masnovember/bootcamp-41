package com.nttdata.bank.client.bankclient.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clients")
public class Client {

  @Id
  private Integer clientId;
  private String clientName;
  private String clientType;
  private String clientDocument;
  private String clientProfile;

}