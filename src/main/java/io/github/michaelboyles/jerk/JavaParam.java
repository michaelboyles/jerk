package io.github.michaelboyles.jerk;

import java.util.Objects;

/// A parameter for a [JavaMethod].
public final class JavaParam {
    private final JavaType type;
    private final String name;

    private JavaParam(JavaType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }

    public JavaType type() {
        return type;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (JavaParam) obj;
        return Objects.equals(this.type, that.type) && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    public static JavaParam of(JavaType type, String name) {
        return new JavaParam(type, name);
    }

    public static JavaParam of(Class<?> type, String name) {
        return new JavaParam(JavaClass.of(type), name);
    }
}
