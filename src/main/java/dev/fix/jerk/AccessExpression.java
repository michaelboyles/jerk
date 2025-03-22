package dev.fix.jerk;

/// An expression used to access a value. Guaranteed to be syntactically legal. Can consist of a chain
/// e.g. `this.car.getEngine().block`.
public final class AccessExpression implements JavaExpression {
    public static AccessExpression accessLocal(CharSequence var) {
        if (isInvalidJavaIdentifier(var)) {
            throw new IllegalArgumentException("Not a valid local variable: '" + var + "'");
        }
        return new AccessExpression(var);
    }

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

    public AccessExpression call(CharSequence method) {
        if (isInvalidJavaIdentifier(method)) {
            throw new IllegalArgumentException("Not a valid method: '" + method + "'");
        }
        return new AccessExpression(expr + '.' + method + "()");
    }

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
