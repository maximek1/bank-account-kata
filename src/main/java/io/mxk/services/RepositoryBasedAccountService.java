package io.mxk.services;

import io.mxk.exceptions.InsufficientFundsException;
import io.mxk.exceptions.InvalidOperationAmountException;
import io.mxk.models.Operation;
import io.mxk.repositories.AccountRepository;
import io.mxk.utils.StatementPrinter;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class RepositoryBasedAccountService implements AccountService {

    private final AccountRepository repository;

    private final StatementPrinter printer;

    private final Clock clock;

    RepositoryBasedAccountService(AccountRepository repository, StatementPrinter printer, Clock clock) {
        this.repository = repository;
        this.printer = printer;
        this.clock = clock;
    }

    public RepositoryBasedAccountService(AccountRepository repository, StatementPrinter printer) {
        this.repository = repository;
        this.printer = printer;
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public void deposit(BigDecimal amount) throws InvalidOperationAmountException {
        if(amount.signum() < 0){ throw new InvalidOperationAmountException("Deposit amount has to be positive");}
        repository.create(
                Operation.getDepositOperation(
                        LocalDateTime.now(clock),
                        amount,
                        getBalance().add(amount)
                )
        );
    }

    @Override
    public void withdraw(BigDecimal amount) throws InsufficientFundsException, InvalidOperationAmountException {
        if(amount.signum() < 0){throw new InvalidOperationAmountException("Withdrawal amount has to be positive");}
        if(getBalance().compareTo(amount) < 0){throw new InsufficientFundsException("Not enough funds to perform withdrawal");}
        repository.create(
                Operation.getWithdrawOperation(
                        LocalDateTime.now(clock),
                        amount,
                        getBalance().subtract(amount)
                )
        );
    }

    @Override
    public void printStatement() {
        printer.printStatement(repository.findAll());
    }

    private BigDecimal getBalance() {
        return repository
                .findMostRecent()
                .map(Operation::getBalance)
                .orElse(BigDecimal.ZERO);
    }
}

