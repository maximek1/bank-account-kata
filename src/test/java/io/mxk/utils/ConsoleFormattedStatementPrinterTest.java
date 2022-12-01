package io.mxk.utils;

import io.mxk.models.Operation;
import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.mxk.fixtures.OperationFixtures.sampleLastOperation;
import static io.mxk.fixtures.OperationFixtures.sampleLastOperationConsoleOutput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(SystemOutGuard.class)
@ExtendWith(MockitoExtension.class)
public class ConsoleFormattedStatementPrinterTest {

    @Mock
    private TextStatementFormatter formatter;

    @InjectMocks
    private ConsoleFormattedStatementPrinter printer;

    @Test
    void shouldPrintNothing(final Capturable stream){
        when(formatter.getFormattedStatementLines(anyList())).thenReturn(Collections.emptyList());

        stream.capture();
        printer.printStatement(Collections.emptyList());
        assertEquals("", stream.getCapturedData());

        verify(formatter).getFormattedStatementLines(Collections.emptyList());
        verifyNoMoreInteractions(formatter);
    }

    @Test
    void shouldPrintOneOperation(final Capturable stream){
        when(formatter.getFormattedStatementLines(List.of(sampleLastOperation))).thenReturn(List.of(sampleLastOperationConsoleOutput));

        stream.capture();
        printer.printStatement(List.of(sampleLastOperation));

        final String[] expectedLines = {
                sampleLastOperationConsoleOutput,
                ""
        };

        assertEquals(String.join(System.getProperty("line.separator"), expectedLines), stream.getCapturedData());

        verify(formatter).getFormattedStatementLines(List.of(sampleLastOperation));
        verifyNoMoreInteractions(formatter);
    }

    @Test
    void shouldPrintTwoOperations(final Capturable stream){
        when(formatter.getFormattedStatementLines(
                List.of(sampleLastOperation,sampleLastOperation)))
                .thenReturn(
                        List.of(sampleLastOperationConsoleOutput,sampleLastOperationConsoleOutput));

        final String[] expectedLines = {
                sampleLastOperationConsoleOutput,
                sampleLastOperationConsoleOutput,
                ""
        };

        List<Operation> operations = Arrays.asList(
                sampleLastOperation,
                sampleLastOperation
        );

        stream.capture();
        printer.printStatement(operations);

        assertEquals(String.join(System.getProperty("line.separator"), expectedLines), stream.getCapturedData());

        verify(formatter).getFormattedStatementLines(operations);
        verifyNoMoreInteractions(formatter);
    }
}

