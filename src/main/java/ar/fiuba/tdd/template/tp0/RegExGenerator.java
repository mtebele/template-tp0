package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generators.RandomGenerator;
import ar.fiuba.tdd.template.tp0.helpers.Tokenizer;
import ar.fiuba.tdd.template.tp0.helpers.Validator;
import ar.fiuba.tdd.template.tp0.models.Token;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {

    private RandomGenerator randomGenerator;
    private Tokenizer tokenizer;
    private Validator validator;

    public RegExGenerator(int maxLength) {
        this.randomGenerator = new RandomGenerator(maxLength);
        this.validator = new Validator();
        this.tokenizer = new Tokenizer(this.validator);
    }

    public List<String> generate(String regEx, int numberOfResults) {

        System.out.print(System.lineSeparator());
        System.out.print("Number of results: " + numberOfResults);
        System.out.print(System.lineSeparator());
        System.out.print("Regex: " + regEx);
        System.out.print(System.lineSeparator());

        validator.validateRegEx(regEx, numberOfResults);

        ArrayList<String> result = new ArrayList<>();

        ArrayList<Token> tokens = tokenizer.tokenize(regEx);
        for (int i = 0; i < numberOfResults; i++) {
            result.add(generateString(tokens));
            System.out.print(i + 1 + ": " + result.get(i));
            System.out.print(System.lineSeparator());
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