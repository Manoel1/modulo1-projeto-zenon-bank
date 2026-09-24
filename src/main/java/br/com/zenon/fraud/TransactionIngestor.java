package br.com.zenon.fraud;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionIngestor {

    public List<Transaction> retornaListArquivosNew(String nomeArquivo, int quantidadeLinhas) {
        Path caminho = Path.of(nomeArquivo);
        try {
            List<String> linhas = Files.readAllLines(caminho);
            return linhas
                    .stream().skip(1)
                    .limit(quantidadeLinhas)
                    .map(this::getTransaction)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar arquivo", e);
        }
    }

    public List<Transaction> retornaListArquivosOld(String nomeArquivo, int quantidadeLinhas) {
        List<Transaction> transactionList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(nomeArquivo);
             Scanner scanner = new Scanner(fis)) {

            int lineCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineCount++;

                if (lineCount == 1) {
                    continue;
                }
                if (lineCount >= quantidadeLinhas) {
                    break;
                }
                var transaction = getTransaction(line);
                transactionList.add(transaction);

            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar arquivo", e);
        }

        return transactionList;

    }

    private Transaction getTransaction(String line) {
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

        return new Transaction(step, type, amount, nameOrig, oldbalanceOrg, newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest, isFraud, isFlaggedFraud);
    }
}