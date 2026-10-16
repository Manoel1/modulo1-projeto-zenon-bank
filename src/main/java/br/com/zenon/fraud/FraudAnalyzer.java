package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {

    private final List<Transaction> transactionList;

    public FraudAnalyzer(List<Transaction> transactionList) {
        Objects.requireNonNull(transactionList);
        this.transactionList = transactionList;

    }

    public long quantidadeFraudes() {
        return transactionStreamFraud()
                .count();
    }

    public List<Transaction> maioresFrauds(int limit) {
        return transactionStreamFraud()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                // .map(Transaction::amount)
                .limit(limit)
                .toList();
    }

    public List<String> nomesFraudulentos() {
        return transactionStreamFraud()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(transaction -> transaction.nameOrig())
                .distinct()
                .limit(5)
                .toList();
    }

    public BigDecimal somaPrejuizo() {
        return transactionStreamFraud()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public Map<TipoPagamento, Long> fraudesPorTransacoes() {
        return transactionStreamFraud()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

    private Stream<Transaction> transactionStreamFraud() {
        return transactionList
                .stream()
                .filter(Transaction::isFraud);
    }


}
