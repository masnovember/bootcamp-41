package com.nttdata.bank.transaction.credit.banktransactioncredit.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Parameters {

  public static final String URL_MS_CREDIT = "http://localhost:8004";

  public static final String OPERATION_DEPOSIT = "101";
  public static final String OPERATION_WITHDRAWAL = "102";
  public static final String OPERATION_DEPOSIT_TRANSFER = "103";
  public static final String OPERATION_WITHDRAWAL_TRANSFER = "104";
  public static final String OPERATION_EXTORT = "105";

  public static final String OPERATION_COMMISSION_EXCESS = "900";

  public static List<String> getRemainingOperations() {
    return new ArrayList<>(Arrays.asList("102","104"));
  };

}
