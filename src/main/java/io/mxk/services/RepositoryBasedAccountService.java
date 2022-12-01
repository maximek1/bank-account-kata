package io.mxk.services;

import io.mxk.exceptions.InvalidOperationAmountException;
import io.mxk.models.Operation;
import io.mxk.repositories.AccountRepository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class RepositoryBasedAccountService implements AccountService{

    private final AccountRepository repository;

    private final Clock clock;

    public RepositoryBasedAccountService(AccountRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
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

    private BigDecimal getBalance() {
        return repository
                .findMostRecent()
                .map(Operation::getBalance)
                .orElse(BigDecimal.ZERO);
    }
}
