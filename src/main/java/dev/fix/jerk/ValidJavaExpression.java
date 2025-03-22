package dev.fix.jerk;

/// A Java expression whose contents have been checked (somehow) and are known to be valid.
final class ValidJavaExpression implements JavaExpression {
    private final String contents;

    ValidJavaExpression(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return contents;
    }
}
