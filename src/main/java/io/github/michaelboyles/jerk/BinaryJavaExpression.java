package io.github.michaelboyles.jerk;

/// Set of Java expressions for binary operators: anything in the form "{operand1} {operator} {operand2}"
public final class BinaryJavaExpression implements JavaExpression {
    private final JavaExpression left;
    private final JavaExpression right;
    private final BinaryOperator operator;

    BinaryJavaExpression(JavaExpression left, JavaExpression right, BinaryOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }

    public static JavaExpression equal(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.EQUALS);
    }

    public static JavaExpression notEqual(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.NOT_EQUALS);
    }

    public static JavaExpression greaterThan(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.GREATER_THAN);
    }

    public static JavaExpression lessThan(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.LESS_THAN);
    }

    public static JavaExpression greaterThanOrEqual(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.GREATER_THAN_OR_EQUALS);
    }

    public static JavaExpression lessThanOrEqual(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.LESS_THAN_OR_EQUALS);
    }

    public static JavaExpression and(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.AND);
    }

    public static JavaExpression or(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.OR);
    }

    public static JavaExpression add(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.ADD);
    }

    public static JavaExpression subtract(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.SUBTRACT);
    }

    public static JavaExpression multiply(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.MULTIPLY);
    }

    public static JavaExpression divide(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.DIVIDE);
    }

    public static JavaExpression modulo(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.MODULO);
    }

    public static JavaExpression bitwiseAnd(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.BITWISE_AND);
    }

    public static JavaExpression bitwiseOr(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.BITWISE_OR);
    }

    public static JavaExpression bitwiseXor(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.BITWISE_XOR);
    }

    public static JavaExpression leftShift(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.LEFT_SHIFT);
    }

    public static JavaExpression rightShift(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.RIGHT_SHIFT);
    }

    public static JavaExpression unsignedRightShift(JavaExpression op1, JavaExpression op2) {
        return new BinaryJavaExpression(op1, op2, BinaryOperator.UNSIGNED_RIGHT_SHIFT);
    }
}
