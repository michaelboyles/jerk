package dev.fix.jerk;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class JavaClass implements JavaType {
    private final FullyQualifiedName fullyQualifiedName;

    public JavaClass(FullyQualifiedName fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public JavaClass(String packageName, String simpleName) {
        this(FullyQualifiedName.of(packageName, simpleName));
    }

    public JavaClass(String fullyQualifiedName) {
        this(FullyQualifiedName.of(fullyQualifiedName));
    }

    public static JavaClass of(Class<?> clazz) {
        return new JavaClass(clazz.getPackageName(), clazz.getSimpleName());
    }

    public JavaType withParam(JavaType typeName) {
        return new ParameterizedJavaType(this, List.of(typeName));
    }

    public boolean isDefaultImported() {
        String packageName = fullyQualifiedName.packageName();
        return fullyQualifiedName.packageName().isEmpty() || packageName.startsWith("java.lang");
    }

    public boolean isInPackage(String packageName) {
        return fullyQualifiedName.packageName().equals(packageName);
    }

    public String packageName() {
        return fullyQualifiedName.packageName();
    }

    public List<String> packageParts() {
        String packageName = fullyQualifiedName.packageName();
        return Arrays.asList(packageName.split("\\."));
    }

    public String simpleName() {
        return fullyQualifiedName.simpleName();
    }

    @Override
    public String canonicalName() {
        return fullyQualifiedName.canonicalName();
    }

    @Override
    public JavaClass box() {
        return this;
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
        return JavaContext.current()
            .map(ctx -> ctx.getOrCreateReference(this))
            .orElseGet(this::canonicalName);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JavaClass that)) return false;
        return Objects.equals(this.fullyQualifiedName, that.fullyQualifiedName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.fullyQualifiedName);
    }
}
