package io.github.michaelboyles;

final class BasicJavaStatement implements JavaStatement {
    private final JavaExpression expression;

    BasicJavaStatement(JavaExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression + ";";
    }
}
