package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(int step, TipoPagamento type, BigDecimal amount, String nameOrig, BigDecimal oldbalanceOrg,
                          BigDecimal newbalanceOrig, String nameDest, BigDecimal oldbalanceDest,
                          BigDecimal newbalanceDest, boolean isFraud, boolean isFlaggedFraud) {


    public Transaction {
        if (step <= 0)
            throw new IllegalArgumentException("step should be positive: " + step);
        if (amount.signum() < 0)
            throw new IllegalArgumentException("step should be positive: " + amount);
        if (oldbalanceOrg.signum() < 0)
            throw new IllegalArgumentException("oldBalance should be positive:: " + oldbalanceOrg);
        if (newbalanceOrig.signum() < 0)
            throw new IllegalArgumentException("newbalance should be positive:: " + newbalanceOrig);
        if (oldbalanceDest.signum() < 0)
            throw new IllegalArgumentException("oldbalanceDest should be positive: " + oldbalanceDest);
        if (newbalanceDest.signum() < 0)
            throw new IllegalArgumentException("newbalanceDest should be positive: " + newbalanceDest);
        if (nameDest.trim().isEmpty())
            throw new IllegalArgumentException("nameDest should be positive: " + nameDest);
        if (nameOrig.trim().isEmpty())
            throw new IllegalArgumentException("nameOrig should be positive: " + nameOrig);

    }
}
