package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Created by mtebele on 20/3/16.
 */
public class QuantifierTest {
    private static final int MAX_LENGTH = 10;
    private static final int NUMBER_RESULTS = 100;

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                        (acc, item) -> {
                            Matcher matcher = pattern.matcher(item);
                            return acc && matcher.find();
                        },
                        (item1, item2) -> item1 && item2);
    }

    @Test
    public void testQuantifierZeroOrOne() {
        assertTrue(validate("a?", NUMBER_RESULTS));
    }

    @Test
    public void testQuantifierZeroToMany() {
        assertTrue(validate("H*", NUMBER_RESULTS));
    }

    @Test
    public void testQuantifierOneToMany() {
        assertTrue(validate("6+", NUMBER_RESULTS));
    }

    @Test
    public void testLotOfQuantifiers() {
        assertTrue(validate("A+B?A*A+B?A*A+B?A*A+B?A*A+B?A*A+B?A*A+B?A*", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnStartWithQuantifier() {
        assertTrue(validate("?", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnStartWithQuantifier2() {
        assertTrue(validate("*", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnStartWithQuantifier3() {
        assertTrue(validate("+", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnConsecutiveQuantifier() {
        assertTrue(validate("abc+?", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnConsecutiveQuantifier2() {
        assertTrue(validate("[a?bc**]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnConsecutiveQuantifier3() {
        assertTrue(validate("[ab+c?]++", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnConsecutiveQuantifier4() {
        assertTrue(validate("ab+c?d++", NUMBER_RESULTS));
    }
}
