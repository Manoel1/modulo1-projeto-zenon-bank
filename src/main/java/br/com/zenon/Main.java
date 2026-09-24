package br.com.zenon;

import br.com.zenon.fraud.TipoPagamento;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        var transaction1 = new Transaction(1, TipoPagamento.PAYMENT, new BigDecimal("9839.64"), "C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.36"), "M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0"), false, false);
        var transaction2 = new Transaction(743, TipoPagamento.CASH_OUT, new BigDecimal("850002.52"), "C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0"), "C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63"), true, false);

        IO.println(transaction1);
        IO.println(transaction2);


        IO.println("-----------------");

        var transactionIngestor = new TransactionIngestor();
        List<Transaction> transactionList = transactionIngestor.retornaListArquivosNew("data/fraud_log.csv", 10);
        transactionList.forEach(IO::println);
        // IO.println(transactionList);
    }
}
