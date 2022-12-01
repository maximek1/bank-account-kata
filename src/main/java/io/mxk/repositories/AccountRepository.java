package io.mxk.repositories;

import io.mxk.models.Operation;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    void create(Operation operation);

    Optional<Operation> findMostRecent();

    List<Operation> findAll();
}
