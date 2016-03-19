package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {
//    private int maxLength;
//    private static final String REGEX_PATTERN = "^(\\.|(\\[.+\\]))$";
//    private RandomGenerator randomGenerator;

    public RegExGenerator(int maxLength) {
//        this.maxLength = maxLength;
//        this.randomGenerator = new RandomGenerator(maxLength);
    }

    public List<String> generate(String regEx, int numberOfResults) {

        // 1) Validar regex
        // 2) Get tokens
        // 3) Generate strings

//        if (!isRegExValid(regEx)) {
//            throw new PatternSyntaxException("La sintaxis no coincide con lo pedido en el TP.", regEx, 0);
//        }

        ArrayList<String> tokens = TokenGenerator.getTokens(regEx);
        return tokens;

//        return new ArrayList<String>() {
//            {
//                add("a");
//                add("b");
//                add("c");
//            }
//        };
    }

//    private ArrayList<String> generateStrings(ArrayList<String> tokens) {
//        ArrayList<String> strings = new ArrayList<>();
//
//        for (String token : tokens) {
//            int quantify = 1;
//            if (RegExUtils.isQuantifier(token.charAt(token.length() - 1))) {
//                quantify = randomGenerator.getNumberFromQuantifier(RegExUtils.Quantifiers.valueOf(token.charAt(token.length() - 1)))
//            }
//        }
//    }

//    private boolean isRegExValid(String regEx) {
//        Pattern pattern = Pattern.compile(REGEX_PATTERN);
//        return pattern.matcher(regEx).find();
//    }
}