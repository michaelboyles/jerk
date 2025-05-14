package io.github.michaelboyles.jerk;

enum BinaryOperator {
    EQUALS("=="),
    NOT_EQUALS("!="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUALS("<="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUALS(">="),
    AND("&&"),
    OR("||"),
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),
    BITWISE_AND("&"),
    BITWISE_OR("|"),
    BITWISE_XOR("^"),
    LEFT_SHIFT("<<"),
    RIGHT_SHIFT(">>"),
    UNSIGNED_RIGHT_SHIFT(">>>");

    private final String operator;

    BinaryOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator;
    }
}
