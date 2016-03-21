package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        assertTrue(validate("123?", NUMBER_RESULTS));
    }

    @Test
    public void testQuantifierZeroToMany() {
        assertTrue(validate("456*", NUMBER_RESULTS));
    }

    @Test
    public void testQuantifierOneToMany() {
        assertTrue(validate("abc+", NUMBER_RESULTS));
    }

    @Test
    public void testLotOfQuantifiers() {
        assertTrue(validate("A+B?A*A+B?A*A+B?A*A+B?A*A+B?A*A+B?A*A+B?A*", NUMBER_RESULTS));
    }

    @Test
    public void testShouldFailOnStartWithQuantifier() {
        assertFailsWithException("?");
        assertFailsWithException("*");
        assertFailsWithException("+");
        assertFailsWithException("?a");
        assertFailsWithException("*b");
        assertFailsWithException("+c");
        assertFailsWithException("?*+a");
        assertFailsWithException("*+?b");
        assertFailsWithException("+?*c");
    }

    @Test
    public void testShouldFailOnConsecutiveQuantifier() {
        assertFailsWithException("abc+?");
        assertFailsWithException("abc*+");
        assertFailsWithException("abc?*");
        assertFailsWithException("[a?bc**]");
        assertFailsWithException("[ab+c?]++");
        assertFailsWithException("ab+c?d++");
    }

    private void assertFailsWithException(String regEx) {
        try {
            validate(regEx, NUMBER_RESULTS);
            fail("Should not pass " + regEx);
        } catch (InvalidRegexException ex) {
            assertTrue(true);
        }
    }
}
