package dev.fix.jerk;

import java.util.List;

/// A fragment of Java code. There are no guarantees about the contents. It could be an expression, a statement,
/// a block, etc.
///
/// The primary goal of creating fragments rather than strings is that they can be bound eagerly, but evaluated lazily.
/// For example, a fragment may contain a binding to a type, e.g. `java.lang.String`, and that type may be rendered as
/// the simple name (`String`) or the fully qualified name (`java.lang.String`) depending on the namespace.
public interface JavaFragment {
    /// Render the fragment. The reason this overrides [Object#toString] is so that Velocity can call it implicitly.
    /// It would be annoying to call e.g. `.render()` all the time
    String toString();

    /// Create a new fragment consisting of this fragment, plus the given one
    default JavaFragment append(JavaFragment that) {
        return CompoundJavaFragment.of(List.of(this, that));
    }

    /// Create an empty fragment
    static JavaFragment empty() {
        return EmptyJavaFragment.INSTANCE;
    }

    static JavaFragment fromString(String code) {
        return new StringJavaFragment(code);
    }

    /// Create a fragment consistent of all the given fragments concatenated together
    static JavaFragment join(List<JavaFragment> children) {
        if (children.size() == 1) children.getFirst();
        return CompoundJavaFragment.of(children);
    }

    static JavaFragment arguments(List<JavaExpression> arguments) {
        return new ArgumentFragment(arguments);
    }
}
