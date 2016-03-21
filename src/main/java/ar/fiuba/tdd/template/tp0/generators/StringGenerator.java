package ar.fiuba.tdd.template.tp0.generators;

import ar.fiuba.tdd.template.tp0.models.Token;

import java.util.ArrayList;

/**
 * Created by mtebele on 21/3/16.
 */
public class StringGenerator {

    private RandomGenerator randomGenerator;

    public StringGenerator(int maxLength) {
        this.randomGenerator = new RandomGenerator(maxLength);
    }

    public ArrayList<String> generateStrings(ArrayList<Token> tokens, int quantity) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            result.add(generateString(tokens));
        }

        return result;
    }

    private String generateString(ArrayList<Token> tokens) {
        StringBuilder builder = new StringBuilder();

        for (Token token : tokens) {
            String generatedString = randomGenerator.generateRandomString(token);
            builder.append(generatedString);
        }

        return builder.toString();
    }
}
