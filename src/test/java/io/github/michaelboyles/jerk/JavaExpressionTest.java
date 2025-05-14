package io.github.michaelboyles.jerk;

import org.junit.jupiter.api.Test;

import static io.github.michaelboyles.jerk.JavaExpression.stringLiteral;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class JavaExpressionTest {
    @Test void stringLiteralWithLineBreaks() {
        assertEquals("\"a\\nb\"", stringLiteral("a\nb").toString());
    }
}
