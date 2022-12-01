package io.mxk.services;

import io.mxk.exceptions.InsufficientFundsException;
import io.mxk.exceptions.InvalidOperationAmountException;
import io.mxk.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZoneId;
import java.util.Optional;

import static io.mxk.fixtures.OperationFixtures.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepositoryBasedAccountServiceTest {

    @Mock
    private AccountRepository repository;

    private AccountService accountService;

    @BeforeEach
    void setClock(){
        final Clock clock = Clock.fixed(
                OperationDate.atZone(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());

        accountService = new RepositoryBasedAccountService(repository, clock);

    }

    @Test
    void shouldPerformDepositWithPreviousOperations(){
        when(repository.findMostRecent()).thenReturn(Optional.of(sampleLastOperation));

        assertDoesNotThrow(() -> accountService.deposit(new BigDecimal(10)));

        verify(repository).findMostRecent();
        verify(repository).create(sampleDepositOperation);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldPerformDepositWithoutPreviousOperations(){
        when(repository.findMostRecent()).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> accountService.deposit(new BigDecimal(10)));

        verify(repository).findMostRecent();
        verify(repository).create(sampleFirstDeposit);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldPerformWithdrawWithPreviousOperation(){
        when(repository.findMostRecent()).thenReturn(Optional.of(sampleLastOperation));

        assertDoesNotThrow(() -> accountService.withdraw(
                new BigDecimal(10))
        );

        verify(repository, times(2)).findMostRecent();
        verify(repository).create(sampleWithdrawOperation);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingWithLowBalance(){
        when(repository.findMostRecent()).thenReturn(Optional.of(sampleLastOperationWithLowBalance));

        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(
                new BigDecimal(10))
        );

        verify(repository).findMostRecent();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenDepositWithInvalidAmount(){
        assertThrows(InvalidOperationAmountException.class, () -> accountService.deposit(
                new BigDecimal(-10))
        );

        verifyNoInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingWithInvalidAmount(){
        assertThrows(InvalidOperationAmountException.class, () -> accountService.withdraw(
                new BigDecimal(-10))
        );

        verifyNoInteractions(repository);
    }

}
