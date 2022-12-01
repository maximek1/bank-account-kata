package io.mxk.utils;

import io.mxk.models.Operation;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TextStatementFormatter implements StatementFormatter {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private final String stringFormat = "%s%25s%15s%n";

    @Override
    public List<String> getFormattedStatementLines(List<Operation> operations) {
        return operations.stream()
                .map(operation -> String.format(stringFormat,
                        operation.getType().name(),
                        operation.getDate().format(dateFormat),
                        decimalFormat.format(operation.getAmount()))

                ).collect(Collectors.toList());
    }
}
