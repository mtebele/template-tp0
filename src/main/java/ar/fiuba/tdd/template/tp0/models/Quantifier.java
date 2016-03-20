package ar.fiuba.tdd.template.tp0.models;

import java.util.Random;

/**
 * Created by mtebele on 20/3/16.
 */
public class Quantifier {

    private QuantifierType type;

    public Quantifier(QuantifierType type) {
        this.type = type;
    }

    public Quantifier(char type) {
        if (type == '?') {
            this.type = QuantifierType.ZERO_OR_ONE;
        } else if (type == '*') {
            this.type = QuantifierType.ZERO_TO_MANY;
        } else if (type == '+') {
            this.type = QuantifierType.ONE_TO_MANY;
        }
    }

    public QuantifierType getType() {
        return type;
    }

    public void setType(QuantifierType type) {
        this.type = type;
    }

    public int getQuantity(int maxLength) {
        Random random = new Random();
        if (type == QuantifierType.ZERO_OR_ONE) {
            return random.nextInt(2);
        } else if (type == QuantifierType.ZERO_TO_MANY) {
            return random.nextInt(maxLength + 1);
        } else if (type == QuantifierType.ONE_TO_MANY) {
            return random.nextInt(maxLength) + 1;
        }
        return 0;
    }
}
