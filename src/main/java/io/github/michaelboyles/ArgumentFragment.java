package io.github.michaelboyles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/// A [JavaFragment] which has the additional semantics that it represents a list of arguments.
final class ArgumentFragment implements JavaFragment {
    private final List<JavaExpression> args;

    ArgumentFragment(List<JavaExpression> args) {
        this.args = new ArrayList<>(args);
    }

    @Override
    public String toString() {
        return args.stream()
            .map(JavaFragment::toString)
            .collect(Collectors.joining(", "));
    }
}
