package io.mxk.services;

import io.mxk.exceptions.InsufficientFundsException;
import io.mxk.exceptions.InvalidOperationAmountException;

import java.math.BigDecimal;

public interface AccountService {

    void deposit(BigDecimal amount) throws InvalidOperationAmountException;

    void withdraw(BigDecimal amount) throws InsufficientFundsException, InvalidOperationAmountException;

    void printStatement();
}
