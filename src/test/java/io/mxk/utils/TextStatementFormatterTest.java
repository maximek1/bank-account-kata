package io.mxk.utils;

import io.mxk.models.Operation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.mxk.fixtures.OperationFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStatementFormatterTest {

    private final TextStatementFormatter formatter = new TextStatementFormatter();

    @Test
    void shouldReturnListOfFormattedStrings(){

        List<String> expectedLines = Arrays.asList(
                sampleDepositConsoleOutput,
                sampleWithdrawConsoleOutput,
                sampleLastOperationConsoleOutput
        );

        List<Operation> operations = Arrays.asList(
                sampleDepositOperation,
                sampleWithdrawOperation,
                sampleLastOperation
        );

        assertEquals(expectedLines, formatter.getFormattedStatementLines(operations));

    }
}
