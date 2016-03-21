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

    private static final int MIN_ASCII_CODE = 32; //0
    private static final int MAX_ASCII_CODE = 126; //255
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
    private Validator validator;

    public RandomGenerator(int maxLength) {
        this.maxLength = maxLength;
        this.random = new Random();
        this.validator = new Validator();
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
                List<Character> characters = getCharactersFromGroup(token);
                int index = random.nextInt(characters.size());
                builder.append(characters.get(index));
            }
        }

        return builder.toString();
    }

    private List<Character> getCharactersFromGroup(Token token) {
        List<Character> characters = RegExUtils.getListFromGroup(token.getValue());
        validator.validateSpecialCharsInGroup(token.getValue(), characters);

        String finalTokenValue = token.getValue().replaceAll("(.*)\\\\(.)(.*)", "$1$2$3");
        return RegExUtils.getListFromGroup(finalTokenValue);
    }
}
