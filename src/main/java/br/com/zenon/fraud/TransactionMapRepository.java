package br.com.zenon.fraud;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepository {


    private final Map<String, Transaction> map;

    public TransactionMapRepository(List<Transaction> transactionList) {
        Objects.requireNonNull(transactionList);
        this.map =
                transactionList
                        .stream()
                        .collect(Collectors.toMap(Transaction::nameOrig, transaction -> transaction));

    }

    @Override
    public Optional<Transaction> findByNome(String nome) {
        return Optional.ofNullable(map.get(nome));
    }
}
