package dev.fix.jerk;

public final class TemplateJavaExpression {
    private final int numPlaceholders;
    private final String template;

    private TemplateJavaExpression(String template) {
        this.template = template;
        this.numPlaceholders = findNumPlaceholders(template);
    }

    public static TemplateJavaExpression of(String template) {
        return new TemplateJavaExpression(template);
    }

    public JavaExpression bind(Object... args) {
        if (numPlaceholders != args.length) {
            throw new IllegalArgumentException(
                "Expected %d arguments, got %d: '%s'".formatted(numPlaceholders, args.length, template)
            );
        }
        return new Bound(args);
    }

    private static int findNumPlaceholders(String template) {
        int numPlaceholders = 0;
        int lastIdx = -2;
        while ((lastIdx = template.indexOf("%s", lastIdx + 2)) >= 0) {
            numPlaceholders++;
        }
        return numPlaceholders;
    }

    private class Bound implements JavaExpression {
        private final Object[] args;

        private Bound(Object[] args) {
            this.args = args;
        }

        @Override
        public String toString() {
            return TemplateJavaFragment.of(template, args).toString();
        }
    }
}
