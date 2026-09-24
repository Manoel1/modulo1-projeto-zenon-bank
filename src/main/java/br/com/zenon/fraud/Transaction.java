package br.com.zenon.fraud;

public record Transaction(int step, TipoPagamento type, Double amount, String nameOrig, double oldbalanceOrg,
                          double newbalanceOrig, String nameDest, double oldbalanceDest,
                          double newbalanceDest, int isFraud, int isFlaggedFraud) {
}
