package dev.fix.jerk;

/// A JavaExpression is a [JavaFragment] with additional semantics that the contents represent an expression.
/// Implementations may - and ideally should - enforce that the contents are syntactically valid expressions.
public interface JavaExpression extends JavaFragment {
    /// {@inheritDoc}
    String toString();

    /// Create a statement from this expression
    default JavaStatement asStatement() {
        return new BasicJavaStatement(this);
    }

    /// Create a new JavaExpression using the null literal
    static JavaExpression nullLiteral() {
        return new ValidJavaExpression("null");
    }

    /// Create a new JavaExpression representing an integer literal value
    static JavaExpression intLiteral(int value) {
        return new ValidJavaExpression(String.valueOf(value));
    }

    /// Create a new JavaExpression representing a long literal value
    static JavaExpression longLiteral(long value) {
        return new ValidJavaExpression(String.valueOf(value) + 'L');
    }

    /// Create a new JavaExpression representing a boolean literal value - true or false
    static JavaExpression booleanLiteral(boolean value) {
        return new ValidJavaExpression(String.valueOf(value));
    }

    /// Create a new Java expression using a string literal containing the given string, which will be escaped if
    /// necessary
    static JavaExpression stringLiteral(String str) {
        return new ValidJavaExpression('"' + str.replace("\"", "\\\"").replace("\n", "\\n") + '"');
    }

    /// Create a JavaExpression without any checks to see whether it's a valid expression.
    ///
    /// If the expression contains any references to Java types, those types won't be eligible for automatic name clash
    /// resolution.
    static JavaExpression unchecked(String java) {
        return new UncheckedJavaExpression(java);
    }
}
