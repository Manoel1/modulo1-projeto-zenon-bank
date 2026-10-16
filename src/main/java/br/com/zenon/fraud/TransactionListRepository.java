package br.com.zenon.fraud;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private final List<Transaction> transactionList;

    public TransactionListRepository(List<Transaction> transactionList) {
        Objects.requireNonNull(transactionList);
        this.transactionList = transactionList;

    }

    public Optional<Transaction> findByNome(String nome) {

        return transactionList.stream()
                .filter(transaction -> transaction.nameOrig().equals(nome))
                .findFirst();
    }

}
