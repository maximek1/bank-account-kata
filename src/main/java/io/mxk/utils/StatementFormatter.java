package io.mxk.utils;

import io.mxk.models.Operation;

import java.util.List;

public interface StatementFormatter {

    List<String> getFormattedStatementLines(List<Operation> operations);
}
