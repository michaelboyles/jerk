package dev.fix.jerk;

/// A JavaExpression is a [JavaFragment] with additional semantics that the contents represent a statement.
public interface JavaStatement extends JavaFragment {
    /// {@inheritDoc}
    String toString();

    static JavaStatement unchecked(String java) {
        return new BasicJavaStatement(new UncheckedJavaExpression(java));
    }
}
