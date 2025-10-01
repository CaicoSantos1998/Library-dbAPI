package io.github.CaicoSantos1998.libraryapi.repository;

import io.github.CaicoSantos1998.libraryapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void transactionSimple() {
        transactionService.execute();
    }

    @Test
    void transactionStateManaged() {
        transactionService.updatingNotUpdate();
    }
}
