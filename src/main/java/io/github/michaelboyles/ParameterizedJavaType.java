package io.github.michaelboyles;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ParameterizedJavaType implements JavaType {
    private final JavaClass className;
    private final List<JavaType> typeArguments;

    public ParameterizedJavaType(JavaClass className, List<JavaType> typeArguments) {
        this.className = className;
        this.typeArguments = typeArguments.stream().map(JavaType::box).toList();
    }

    @Override
    public ParameterizedJavaType box() {
        return this;
    }

    @Override
    public String canonicalName() {
        return className.canonicalName();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public String defaultLiteral() {
        return "null";
    }

    @Override
    public String toString() {
        return className.toString() + typeArguments.stream()
            .map(JavaType::toString)
            .collect(Collectors.joining(", ", "<", ">"));
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ParameterizedJavaType other)) return false;
        if (!Objects.equals(this.className, other.className)) return false;
        return Objects.equals(this.typeArguments, other.typeArguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.className, this.typeArguments);
    }
}
