package ar.fiuba.tdd.template.tp0;

import java.util.Random;

/**
 * Created by mtebele on 19/3/16.
 */
public class RandomGenerator {

    /* RULE: random.nextInt(max - min + 1) + min */

    private int maxLength;

    public RandomGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getNumberFromQuantifier(char quantifier) {
        Random random = new Random();
        if (quantifier == RegExUtils.Quantifiers.ZERO_TO_ONE.getValue()) {
            return random.nextInt(2);
        } else if (quantifier == RegExUtils.Quantifiers.ZERO_TO_MANY.getValue()) {
            return random.nextInt(maxLength + 1);
        } else if (quantifier == RegExUtils.Quantifiers.ONE_TO_MANY.getValue()) {
            return random.nextInt(maxLength) + 1;
        }
        return 0;
    }

    public char generateRandomChar() {
        Random random = new Random();
        return (char) (32 + random.nextInt(94));
    }

    public String generateRandomString(int quantity) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            builder.append((char) (32 + random.nextInt(94)));
        }

        return builder.toString();
    }
}
