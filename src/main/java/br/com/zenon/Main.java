package br.com.zenon;

import br.com.zenon.fraud.TipoPagamento;
import br.com.zenon.fraud.Transaction;

public class Main {
    public static void main(String[] args) {

        var transaction1 = new Transaction(1, TipoPagamento.PAYMENT, 9839.64, "C1231006815", 170136.0, 160296.36, "M1979787155", 0.0, 0.0, 0, 0);
        var transaction2 = new Transaction(743, TipoPagamento.CASH_OUT, 850002.52, "C1280323807", 850002.52, 0.0, "C873221189", 6510099.11, 7360101.63, 1, 0);

        System.out.println(transaction1);
        System.out.println(transaction2);
    }
}
