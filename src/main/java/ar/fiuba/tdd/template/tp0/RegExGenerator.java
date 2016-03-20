package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {
    //    private static final String REGEX_PATTERN = "^(\\.|(\\[.+\\]))$";
    private RandomGenerator randomGenerator;
    private TokenGenerator tokenGenerator;

    public RegExGenerator(int maxLength) {
        this.randomGenerator = new RandomGenerator(maxLength);
        this.tokenGenerator = new TokenGenerator();
    }

    public List<String> generate(String regEx, int numberOfResults) {

        // TODO: chequear sintaxis de regEx.

        ArrayList<String> result = new ArrayList<>();

        ArrayList<String> tokens = tokenGenerator.getTokens(regEx);
        for (int i = 0; i < numberOfResults; i++) {
            result.add(generateString(tokens));
        }

        return result;
    }

    private String generateString(ArrayList<String> tokens) {
        StringBuilder builder = new StringBuilder();

        for (String token : tokens) {
            String finalToken = token;
            int quantify = 1;
            char lastChar = token.charAt(token.length() - 1);
            if (RegExUtils.isQuantifier(lastChar)) {
                quantify = randomGenerator.getNumberFromQuantifier(token.charAt(lastChar));
                finalToken = token.replaceFirst(".$", "");
            }

            builder.append(handleString(finalToken, quantify));
        }

        return builder.toString();
    }

    private String handleString(String token, int quantify) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < token.length(); i++) {
            char charValue = token.charAt(i);

            if (charValue == '.') {
                builder.append(randomGenerator.generateRandomString(quantify));
            }
        }
        return builder.toString();
    }
}