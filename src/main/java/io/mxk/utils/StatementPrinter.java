package io.mxk.utils;

import io.mxk.models.Operation;

import java.util.List;

public interface StatementPrinter {

    void printStatement(List<Operation> operations);

}
