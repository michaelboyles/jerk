package io.github.michaelboyles.jerk;

import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Arrays;
import java.util.List;

public final class TemplateJavaFragment implements JavaFragment {
    private final String template;
    private final List<Object> args;

    private TemplateJavaFragment(String template, List<Object> args) {
        this.template = template;
        this.args = args;
    }

    @CheckReturnValue
    public static TemplateJavaFragment of(String template, Object... args) {
        return new TemplateJavaFragment(template, Arrays.asList(args));
    }

    @Override
    public String toString() {
        int nthArg = 0;
        int lastIdx = 0;
        int idx;
        StringBuilder builder = new StringBuilder();
        while ((idx = template.indexOf("%s", lastIdx)) >= 0) {
            builder.append(template, lastIdx, idx);
            appendArgument(builder, this.args.get(nthArg++));
            lastIdx = idx + 2;
        }
        builder.append(template.substring(lastIdx));
        return builder.toString();
    }

    private void appendArgument(StringBuilder builder, Object arg) {
        int indentation = currentIndentation(builder);
        String[] lines = stringifyArg(arg).split("\\R");
        if (lines.length == 1) {
            builder.append(lines[0]);
            return;
        }
        for (int i = 0; i < lines.length; ++i) {
            if (i != 0) {
                builder.append(" ".repeat(indentation));
            }
            builder.append(lines[i]);
            if (i + 1 < lines.length) {
                builder.append("\n");
            }
        }
    }

    private String stringifyArg(Object arg) {
        if (arg instanceof Class<?> clazz) {
            return JavaClass.of(clazz).toString();
        }
        return arg.toString();
    }

    private int currentIndentation(StringBuilder builder) {
        int numSpaces = 0;
        for (int i = builder.length() - 1; i >= 0; --i) {
            char ch = builder.charAt(i);
            if (ch == ' ') {
                numSpaces++;
            }
            else {
                break;
            }
        }
        return numSpaces;
    }
}
