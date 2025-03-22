package dev.fix.jerk;

/// A type within Java's type system. This includes primitives, classes, enums, interfaces, and records.
///
/// This interface and its subtypes are primarily designed to be used for managing imports. There isn't necessarily
/// the information available to provide the same level of info about the type as with via reflection, but some
/// metadata is provided for convenience.
public interface JavaType {
    /// Box the type. If this type is a primitive, you will get a [JavaClass] of the java.lang class of the boxed type.
    /// For every other type, this method returns the exact same instance.
    JavaType box();

    /// The canonical name of the type. That is, the full name including the package, if any. This is the form which is
    /// suitable for importing. For inner classes, the form com.foo.SomeClass.SomeInnerClass is used (rather than the
    /// $-separated notation that is sometimes used).
    String canonicalName();

    /// Whether this type is a primitive
    boolean isPrimitive();

    /// The literal which is Java's default for an uninitialized field. For reference types, that's null. For primitives,
    /// it's zero or false.
    String defaultLiteral();
}
