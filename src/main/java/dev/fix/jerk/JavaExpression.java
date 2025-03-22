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

    /// Create a new Java expression using the null literal
    static JavaExpression nullLiteral() {
        return new ValidJavaExpression("null");
    }

    static JavaExpression intLiteral(int value) {
        return new ValidJavaExpression(String.valueOf(value));
    }

    static JavaExpression longLiteral(long value) {
        return new ValidJavaExpression(String.valueOf(value) + 'L');
    }

    static JavaExpression booleanLiteral(boolean value) {
        return new ValidJavaExpression(String.valueOf(value));
    }

    /// Create a new Java expression using a string literal containing the given string, which will be escaped if
    /// necessary
    static JavaExpression stringLiteral(String str) {
        return new ValidJavaExpression('"' + str.replace("\"", "\\\"") + '"');
    }

    static JavaExpression unchecked(String java) {
        return new UncheckedJavaExpression(java);
    }
}
