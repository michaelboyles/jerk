package dev.fix.jerk;

public interface JavaType {
    JavaType box();

    String canonicalName();

    boolean isPrimitive();

    /// The literal which is Java's default for an uninitialized field. For reference types, that's null. For primitives,
    /// it's zero or false.
    String defaultLiteral();
}
