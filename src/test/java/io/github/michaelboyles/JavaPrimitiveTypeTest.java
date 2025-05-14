package io.github.michaelboyles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaPrimitiveTypeTest {
    @Test void box() {
        assertEquals(JavaClass.of(Byte.class), JavaPrimitiveType.BYTE.box());
        assertEquals(JavaClass.of(Short.class), JavaPrimitiveType.SHORT.box());
        assertEquals(JavaClass.of(Integer.class), JavaPrimitiveType.INT.box());
        assertEquals(JavaClass.of(Long.class), JavaPrimitiveType.LONG.box());
        assertEquals(JavaClass.of(Float.class), JavaPrimitiveType.FLOAT.box());
        assertEquals(JavaClass.of(Double.class), JavaPrimitiveType.DOUBLE.box());
        assertEquals(JavaClass.of(Boolean.class), JavaPrimitiveType.BOOLEAN.box());
        assertEquals(JavaClass.of(Character.class), JavaPrimitiveType.CHAR.box());
    }

    @Test void isPrimitive() {
        for (JavaPrimitiveType value : JavaPrimitiveType.values()) {
            assertTrue(value.isPrimitive());
        }
    }

    @Test void defaultLiteral() {
        assertEquals("0", JavaPrimitiveType.INT.defaultLiteral());
        assertEquals("false", JavaPrimitiveType.BOOLEAN.defaultLiteral());
    }

    @Test void canonicalName() {
        assertEquals("byte", JavaPrimitiveType.BYTE.canonicalName());
        assertEquals("short", JavaPrimitiveType.SHORT.canonicalName());
        assertEquals("int", JavaPrimitiveType.INT.canonicalName());
        assertEquals("long", JavaPrimitiveType.LONG.canonicalName());
        assertEquals("float", JavaPrimitiveType.FLOAT.canonicalName());
        assertEquals("double", JavaPrimitiveType.DOUBLE.canonicalName());
        assertEquals("boolean", JavaPrimitiveType.BOOLEAN.canonicalName());
        assertEquals("char", JavaPrimitiveType.CHAR.canonicalName());
    }
}