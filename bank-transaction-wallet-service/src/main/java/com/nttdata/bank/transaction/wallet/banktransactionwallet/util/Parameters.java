package com.nttdata.bank.transaction.wallet.banktransactionwallet.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Parameters {

  public static final String OPERATION_DEPOSIT = "101";
  public static final String OPERATION_WITHDRAWAL = "102";

  public static List<String> getRemainingOperations() {
    return new ArrayList<>(Arrays.asList("102","104"));
  };

}
