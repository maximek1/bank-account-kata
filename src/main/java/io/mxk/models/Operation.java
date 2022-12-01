package io.mxk.models;

import io.mxk.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Operation {

    private final OperationType type;

    private final LocalDateTime date;

    private final BigDecimal amount;

    private final BigDecimal balance;

    private Operation(OperationType type, LocalDateTime date, BigDecimal amount, BigDecimal balance) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public static Operation getDepositOperation(LocalDateTime date, BigDecimal amount, BigDecimal balance){
        return new Operation(
                OperationType.DEPOSIT,
                date,
                amount,
                balance
        );
    }

    public static Operation getWithdrawOperation(LocalDateTime date, BigDecimal amount, BigDecimal balance){
        return new Operation(
                OperationType.WITHDRAWAL,
                date,
                amount.negate(),
                balance
        );
    }

    public OperationType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return type == operation.type && Objects.equals(date, operation.date) && Objects.equals(amount, operation.amount) && Objects.equals(balance, operation.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, date, amount, balance);
    }
}
