package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.models.Token;
import ar.fiuba.tdd.template.tp0.models.TokenType;

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

    public int quantifyToken(Token token) {
        return token.getQuantity(maxLength);
    }

    public char generateRandomChar() {
        Random random = new Random();
        return (char) (32 + random.nextInt(94));
    }

    public String generateRandomString(Token token) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        int quantity = quantifyToken(token);

        for (int i = 0; i < quantity; i++) {
            if (token.getTokenType() == TokenType.DOT) {
                builder.append((char) (32 + random.nextInt(94)));
            } else if (token.getTokenType() == TokenType.LITERAL) {
                builder.append(token.getValue());
            }
        }

        return builder.toString();
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
