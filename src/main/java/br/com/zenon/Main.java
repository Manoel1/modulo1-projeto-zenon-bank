package br.com.zenon;

import br.com.zenon.fraud.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        var transaction1 = new Transaction(1, TipoPagamento.PAYMENT, new BigDecimal("9839.64"), "C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.36"), "M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0"), false, false);
        var transaction2 = new Transaction(743, TipoPagamento.CASH_OUT, new BigDecimal("850002.52"), "C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0"), "C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63"), true, false);

        IO.println(transaction1);
        IO.println(transaction2);


        IO.println("-----------------------------------------------------------------------------------------------------------------------");

        var transactionIngestor = new TransactionIngestor();
        List<Transaction> transactionListError = transactionIngestor.retornaListArquivosNew("data/fraud_log.csv", 100_000);
        IO.println(transactionListError.size());
        //
        var fraud = new FraudAnalyzer(transactionListError);
        IO.println("1. Total de Fraudes: " + fraud.quantidadeFraudes());
        //
        var maioresFraudes = fraud.maioresFrauds(3);
        IO.println("2. Top 3 Fraudes de Maior Valor: ");
        maioresFraudes.stream().map(Transaction::amount).forEach(IO::println);
        //
        IO.println("3. Clientes Suspeitos: ");
        var nomesFraudulentos = fraud.nomesFraudulentos();
        nomesFraudulentos.forEach(IO::println);
        IO.println("4. Prejuízo Total: " + fraud.somaPrejuizo());
        //
        Map<TipoPagamento, Long> fraudesPorTransacoes = fraud.fraudesPorTransacoes();
        IO.println("5. Fraudes por Tipo: " + fraudesPorTransacoes);
        fraudesPorTransacoes.forEach((tipoPagamento, count) -> IO.println("- %s: %d".formatted(tipoPagamento, count)));

        IO.println("-----------------------------------------------------------------------------------------------------------------------");
        var transactionListRepository = new TransactionListRepository(transactionListError);
        String name = "C1868032458";
        long starTime = System.nanoTime();
        transactionListRepository.findByNome(name).ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente: " + name));
        long endTime = System.nanoTime();
        IO.println("Tempo de busca em List: (ms) - " + (endTime - starTime) / 1_000_000.0);

        IO.println("-----------------------------------------------------------------------------------------------------------------------");

        var transactionMapRepository = new TransactionMapRepository(transactionListError);
        starTime = System.nanoTime();
        transactionMapRepository.findByNome(name).ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente: " + name));
        endTime = System.nanoTime();
        IO.println("Tempo de busca em Map: (ms) - " + (endTime - starTime) / 1_000_000.0);

    }
}
