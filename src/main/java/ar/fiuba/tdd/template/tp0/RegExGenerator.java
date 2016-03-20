package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.models.Token;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {
    //    private static final String REGEX_PATTERN = "^(\\.|(\\[.+\\]))$";
    private RandomGenerator randomGenerator;
    private Tokenizer tokenizer;

    public RegExGenerator(int maxLength) {
        this.randomGenerator = new RandomGenerator(maxLength);
        this.tokenizer = new Tokenizer();
    }

    public List<String> generate(String regEx, int numberOfResults) {

        // TODO: chequear sintaxis de regEx.

        ArrayList<String> result = new ArrayList<>();

        ArrayList<Token> tokens = tokenizer.tokenize(regEx);
        for (int i = 0; i < numberOfResults; i++) {
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