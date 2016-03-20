package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.models.Token;
import ar.fiuba.tdd.template.tp0.models.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mtebele on 19/3/16.
 */
public class RandomGenerator {

    /* RULE: random.nextInt(max - min + 1) + min */

    private static final int MIN_ASCII_CODE = 0; //32
    private static final int MAX_ASCII_CODE = 255; //126
    private static final List<Integer> FORBIDDEN_ASCII_CODES = new ArrayList<Integer>() {
        {
            add(10);
            add(13);
            add(127);
            add(129);
            add(141);
            add(143);
            add(144);
            add(157);
        }
    };

    private int maxLength;
    private Random random;

    public RandomGenerator(int maxLength) {
        this.maxLength = maxLength;
        this.random = new Random();
    }

    public int quantifyToken(Token token) {
        return token.getQuantity(maxLength);
    }

    public String generateRandomString(Token token) {
        StringBuilder builder = new StringBuilder();
        int quantity = quantifyToken(token);

        for (int i = 0; i < quantity; i++) {
            if (token.getTokenType() == TokenType.DOT) {
                int ascii = (MIN_ASCII_CODE + random.nextInt(MAX_ASCII_CODE - MIN_ASCII_CODE));
                while (FORBIDDEN_ASCII_CODES.contains(ascii)) {
                    ascii = (MIN_ASCII_CODE + random.nextInt(MAX_ASCII_CODE - MIN_ASCII_CODE));
                }
                builder.append((char) ascii);
            } else if (token.getTokenType() == TokenType.LITERAL) {
                builder.append(token.getValue());
            } else if (token.getTokenType() == TokenType.GROUP) {
                List<Character> characters = RegExUtils.getListFromGroup(token.getValue());
                int index = random.nextInt(characters.size());
                builder.append(characters.get(index));
            }
        }

        return builder.toString();
    }
}
