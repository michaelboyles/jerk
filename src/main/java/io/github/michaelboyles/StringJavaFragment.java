package io.github.michaelboyles;

/// A fragment of Java consisting of a simple string
final class StringJavaFragment implements JavaFragment {
    private final String contents;

    StringJavaFragment(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return contents;
    }
}
