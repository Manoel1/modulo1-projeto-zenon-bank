package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class TransactionIngestor {

    public List<Transaction> retornaListArquivosNew(String nomeArquivo, int quantidadeLinhas) {
        Path caminho = Path.of(nomeArquivo);
        try {
            List<String> linhas = Files.readAllLines(caminho);
            return linhas
                    .stream().skip(1)
                    .limit(quantidadeLinhas)
                    .map(this::getTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar arquivo: " + nomeArquivo, e);
        }
    }

    private Optional<Transaction> getTransaction(String line) {
        try {
            String[] linhas = line.split(",");
            int step = Integer.parseInt(linhas[0]);
            TipoPagamento type = TipoPagamento.valueOf(linhas[1]);
            BigDecimal amount = new BigDecimal(linhas[2]);
            String nameOrig = linhas[3];
            BigDecimal oldbalanceOrg = new BigDecimal(linhas[4]);
            BigDecimal newbalanceOrig = new BigDecimal(linhas[5]);
            String nameDest = linhas[6];
            BigDecimal oldbalanceDest = new BigDecimal(linhas[7]);
            BigDecimal newbalanceDest = new BigDecimal(linhas[8]);
            boolean isFraud = !linhas[9].equals("0");
            boolean isFlaggedFraud = linhas[9].equals("0") ? false : true;

            return Optional.of(new Transaction(step, type, amount, nameOrig, oldbalanceOrg, newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest, isFraud, isFlaggedFraud));
        } catch (Exception e) {
            System.err.println("error: " + line + e);
            //e.printStackTrace();
            return Optional.empty();

        }
    }
}