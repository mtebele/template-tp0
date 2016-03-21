package ar.fiuba.tdd.template.tp0.generators;

import ar.fiuba.tdd.template.tp0.helpers.Validator;
import ar.fiuba.tdd.template.tp0.models.Token;
import ar.fiuba.tdd.template.tp0.models.TokenType;
import ar.fiuba.tdd.template.tp0.utils.StringUtils;
import ar.fiuba.tdd.template.tp0.utils.TokenUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by mtebele on 19/3/16.
 */
public class RandomGenerator {

    /* RULE: random.nextInt(max - min + 1) + min */

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
                int ascii = StringUtils.getValidRandomAsciiCode();
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
        validator.validateSpecialCharsInGroup(token);

        String finalTokenValue = token.getValue().replace("\\", "");
        return TokenUtils.getListFromGroup(finalTokenValue);
    }
}
