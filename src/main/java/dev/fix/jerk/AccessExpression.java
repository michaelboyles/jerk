package dev.fix.jerk;

/// An expression used to access a value. Guaranteed to be syntactically legal. This can consist of a chain
/// e.g. `this.car.getEngine().block`.
public final class AccessExpression implements JavaExpression {
    /// Create a new AccessExpression starting with the given local variable
    public static AccessExpression accessLocal(CharSequence localVariable) {
        if (isInvalidJavaIdentifier(localVariable)) {
            throw new IllegalArgumentException("Not a valid local variable: '" + localVariable + "'");
        }
        return new AccessExpression(localVariable);
    }

    /// Create a new AccessExpression starting with the given class field. It will be discriminated with from
    /// any locals which may share the name by prefixing the field with `this.`.
    public static AccessExpression accessField(CharSequence field) {
        if (isInvalidJavaIdentifier(field)) {
            throw new IllegalArgumentException("Not a valid field: '" + field + "'");
        }
        return new AccessExpression("this." + field);
    }

    private final String expr;

    private AccessExpression(CharSequence expr) {
        this.expr = expr.toString();
    }

    /// Create a new expression from the current expression, plus a call to the given method, by adding e.g. `.method()`
    /// @return a new access expression
    public AccessExpression call(CharSequence method) {
        if (isInvalidJavaIdentifier(method)) {
            throw new IllegalArgumentException("Not a valid method: '" + method + "'");
        }
        return new AccessExpression(expr + '.' + method + "()");
    }

    /// Create a new expression from the current expression, plus access of a field, by adding e.g. `.field`.
    /// @return a new access expression
    public AccessExpression field(CharSequence field) {
        if (isInvalidJavaIdentifier(field)) {
            throw new IllegalArgumentException("Not a valid field name: '" + field + "'");
        }
        return new AccessExpression(expr + '.' + field);
    }

    @Override
    public String toString() {
        return expr;
    }

    private static boolean isInvalidJavaIdentifier(CharSequence identifier) {
        if (identifier == null || identifier.toString().isBlank()) return true;
        if (!Character.isJavaIdentifierStart(identifier.charAt(0))) return true;
        for (int i = 1; i < identifier.length(); ++i) {
            if (!Character.isJavaIdentifierPart(identifier.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
