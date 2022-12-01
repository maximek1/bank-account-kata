package io.mxk.fixtures;

import io.mxk.models.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

public class OperationFixtures {

    public static final LocalDateTime OperationDate = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0, 0);

    public static final Operation sampleDepositOperation =
            Operation.getDepositOperation(
                    OperationDate,
                    new BigDecimal(10),
                    new BigDecimal(80)
            );

    public static final Operation sampleWithdrawOperation =
            Operation.getWithdrawOperation(
                    OperationDate,
                    new BigDecimal(10),
                    new BigDecimal(60)
            );

    public static final Operation sampleFirstDeposit =
            Operation.getDepositOperation(
                    OperationDate,
                    new BigDecimal(10),
                    new BigDecimal(10)
            );

    public static final Operation sampleLastOperation =
            Operation.getWithdrawOperation(
                    OperationDate,
                    new BigDecimal(10),

                    new BigDecimal(70)
            );

    public static final Operation sampleLastOperationWithLowBalance =
            Operation.getWithdrawOperation(
                    OperationDate,
                    new BigDecimal(10),
                    new BigDecimal(0)
            );

    public static final String sampleDepositConsoleOutput = String.format("%s%25s%15s%n", "DEPOSIT", "01-01-2022 00:00:00", "10,00");
    public static final String sampleWithdrawConsoleOutput = String.format("%s%25s%15s%n", "WITHDRAWAL", "01-01-2022 00:00:00", "-10,00");
    public static final String sampleLastOperationConsoleOutput = String.format("%s%25s%15s%n", "WITHDRAWAL", "01-01-2022 00:00:00", "-10,00");
}
