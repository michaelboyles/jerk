package dev.fix.jerk;

/// A Java expression whose contents have not been checked, and so is not known to be valid (but may be).
final class UncheckedJavaExpression implements JavaExpression {
    private final String contents;

    UncheckedJavaExpression(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return contents;
    }
}

