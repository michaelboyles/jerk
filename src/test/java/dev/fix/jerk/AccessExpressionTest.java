package dev.fix.jerk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccessExpressionTest {
    @Test void fieldAndCall() {
        assertEquals(
            "this.foo.bar.baz()",
            AccessExpression.accessField("foo").field("bar").call("baz").toString()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "1hello", "" })
    void illegalFieldName(String fieldName) {
        assertThrows(IllegalArgumentException.class, () ->
            AccessExpression.accessField(fieldName)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "1hello", "" })
    void illegalLocalVariable(String local) {
        assertThrows(IllegalArgumentException.class, () ->
            AccessExpression.accessLocal(local)
        );
    }

    @Test void illegalCall() {
        assertThrows(IllegalArgumentException.class, () ->
            AccessExpression.accessLocal("test").call("hel,lo")
        );
    }

    @Test void illegalField() {
        assertThrows(IllegalArgumentException.class, () ->
            AccessExpression.accessLocal("test").field(null)
        );
    }
}