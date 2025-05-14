package io.github.michaelboyles;

/// A JavaExpression is a [JavaFragment] with additional semantics that the contents represent a statement.
public interface JavaStatement extends JavaFragment {
    /// {@inheritDoc}
    String toString();

    /// Create a JavaStatement without any checks to see whether it's a valid statement.
    ///
    /// If the expression contains any references to Java types, those types won't be eligible for automatic name clash
    /// resolution. This method should generally be avoided for alternatives that are safer and/or that do lazy
    /// type binding.
    static JavaStatement unchecked(String java) {
        return new BasicJavaStatement(new UncheckedJavaExpression(java));
    }
}
