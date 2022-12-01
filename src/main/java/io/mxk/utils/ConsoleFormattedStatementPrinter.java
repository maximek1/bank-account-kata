package io.mxk.utils;

import io.mxk.models.Operation;

import java.util.List;

public class ConsoleFormattedStatementPrinter implements StatementPrinter {

    private final StatementFormatter formatter;

    public ConsoleFormattedStatementPrinter(StatementFormatter statementFormatter) {
        this.formatter = statementFormatter;
    }

    @Override
    public void printStatement(List<Operation> operations) {
        formatter.getFormattedStatementLines(operations).forEach(
                System.out::println
        );
    }
}
