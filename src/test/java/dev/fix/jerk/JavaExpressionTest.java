package dev.fix.jerk;

import org.junit.jupiter.api.Test;

import static dev.fix.jerk.JavaExpression.stringLiteral;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class JavaExpressionTest {
    @Test void stringLiteralWithLineBreaks() {
        assertEquals("\"a\\nb\"", stringLiteral("a\nb").toString());
    }
}
