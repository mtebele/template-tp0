package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generators.StringGenerator;
import ar.fiuba.tdd.template.tp0.helpers.Tokenizer;
import ar.fiuba.tdd.template.tp0.helpers.Validator;
import ar.fiuba.tdd.template.tp0.models.Token;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {

    private static final boolean SHOW_LOGS = true;

    private StringGenerator stringGenerator;
    private Tokenizer tokenizer;
    private Validator validator;

    public RegExGenerator(int maxLength) {
        this.stringGenerator = new StringGenerator(maxLength);
        this.validator = new Validator();
        this.tokenizer = new Tokenizer(this.validator);
    }

    public List<String> generate(String regEx) {
        return generate(regEx, 1);
    }

    public List<String> generate(String regEx, int numberOfResults) {

        if (SHOW_LOGS) {
            System.out.print(System.lineSeparator());
            System.out.print("Number of results: " + numberOfResults);
            System.out.print(System.lineSeparator());
            System.out.print("RegEx: " + regEx);
            System.out.print(System.lineSeparator());
        }

        validator.validateRegEx(regEx, numberOfResults);

        ArrayList<Token> tokens = tokenizer.tokenize(regEx);
        ArrayList<String> results = stringGenerator.generateStrings(tokens, numberOfResults);

        if (SHOW_LOGS) {
            for (int i = 0; i < results.size(); i++) {
                System.out.print(i + 1 + ": " + results.get(i));
                System.out.print(System.lineSeparator());
            }
        }

        return results;
    }
}