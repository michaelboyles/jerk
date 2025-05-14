package io.github.michaelboyles.jerk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FullyQualifiedNameTest {
    static class Foo {
        static class Bar {
        }
    }

    @Test void topLevelClass() {
        var fqn = FullyQualifiedName.of(FullyQualifiedNameTest.class);
        assertEquals("io.github.michaelboyles.jerk", fqn.packageName());
        assertEquals("FullyQualifiedNameTest", fqn.simpleName());
    }

    @Test void innerClass() {
        var fqn = FullyQualifiedName.of(Foo.class);
        assertEquals("io.github.michaelboyles.jerk", fqn.packageName());
        assertEquals("io.github.michaelboyles.jerk.FullyQualifiedNameTest$Foo", fqn.toString());
        assertEquals("Foo", fqn.simpleName());
    }

    @Test void innerClassOfInnerClass() {
        var fqn = FullyQualifiedName.of(Foo.Bar.class);
        assertEquals("io.github.michaelboyles.jerk", fqn.packageName());
        assertEquals("io.github.michaelboyles.jerk.FullyQualifiedNameTest$Foo$Bar", fqn.toString());
        assertEquals("Bar", fqn.simpleName());
    }

    @Test void localClass() {
        class Local {
        }
        assertThrows(IllegalArgumentException.class, () -> FullyQualifiedName.of(Local.class));
    }

    @Test void spaceInPackage() {
        assertThrows(IllegalArgumentException.class, () -> FullyQualifiedName.of("com. foo", "bar"));
    }

    @Test void emptyType() {
        assertThrows(IllegalArgumentException.class, () -> FullyQualifiedName.of("com.foo", ""));
    }

    @Test void spaceInType() {
        assertThrows(IllegalArgumentException.class, () -> FullyQualifiedName.of("com.foo", "b ar"));
    }

    @Test void packageContainsKeyword() {
        assertThrows(IllegalArgumentException.class, () -> FullyQualifiedName.of("com.double", "bar"));
    }

    @Test void parentTypeContainsSpace() {
        assertThrows(IllegalArgumentException.class, () -> FullyQualifiedName.of("com.foo.Fo o$Bar"));
    }

    @Test void parseString() {
        var fqn = FullyQualifiedName.of("com.foo.bar.Baz");
        assertEquals("com.foo.bar", fqn.packageName());
        assertEquals("Baz", fqn.simpleName());
    }

    @Test void parseInnerClassString() {
        var fqn = FullyQualifiedName.of("com.foo.bar.Baz$Jazz");
        assertEquals("com.foo.bar", fqn.packageName());
        assertEquals("com.foo.bar.Baz$Jazz", fqn.toString());
        assertEquals("Jazz", fqn.simpleName());
    }

    @Test void parseUnnamedPackage() {
        var fqn = FullyQualifiedName.of("Foo");
        assertEquals("", fqn.packageName());
        assertEquals("Foo", fqn.toString());
        assertEquals("Foo", fqn.simpleName());
    }
}