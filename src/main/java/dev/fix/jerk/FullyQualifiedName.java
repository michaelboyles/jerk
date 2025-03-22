package dev.fix.jerk;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FullyQualifiedName {
    private static final Set<String> RESERVED_KEYWORDS = Set.of(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
        "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
        "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
        "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
        "volatile", "while"
    );

    private final String packageName;
    private final List<String> parentClasses;
    private final String typeName;

    private FullyQualifiedName(String packageName, List<String> parentClasses, String typeName) {
        for (String parentClass : parentClasses) {
            if (!isValidIdentifier(parentClass)) {
                throw new IllegalArgumentException("Invalid parent type " + parentClass);
            }
        }
        this.packageName = validatePackage(packageName);
        this.parentClasses = parentClasses;
        this.typeName = validateType(typeName);
    }

    public String packageName() {
        return packageName;
    }

    public String simpleName() {
        return typeName;
    }

    /// Returns the form where inner classes are not easily distinguishable from classes - everything is dot separated.
    public String canonicalName() {
        var type = Stream.concat(
            parentClasses.stream(),
            Stream.of(typeName)
        ).collect(Collectors.joining("."));
        if (packageName.isBlank()) return type;
        return packageName + "." + type;
    }

    /// Returns the form where inner classes are denoted by dollar signs ($)
    @Override
    public String toString() {
        var type = Stream.concat(
            parentClasses.stream(),
            Stream.of(typeName)
        ).collect(Collectors.joining("$"));
        if (packageName.isBlank()) return type;
        return packageName + "." + type;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FullyQualifiedName that)) return false;
        if (!Objects.equals(packageName, that.packageName)) return false;
        if (!Objects.equals(parentClasses, that.parentClasses)) return false;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, parentClasses, typeName);
    }

    public static FullyQualifiedName of(String fullyQualifiedName) {
        int lastDotIndex = fullyQualifiedName.lastIndexOf('.');
        String packageName = (lastDotIndex == -1) ? "" : fullyQualifiedName.substring(0, lastDotIndex);
        String typeName = (lastDotIndex == -1) ? fullyQualifiedName : fullyQualifiedName.substring(lastDotIndex + 1);
        return FullyQualifiedName.of(packageName, typeName);
    }

    public static FullyQualifiedName of(String packageName, String typeName) {
        var tokens = typeName.split("\\$");
        return new FullyQualifiedName(
            packageName,
            tokens.length > 1 ? Arrays.asList(Arrays.copyOf(tokens, tokens.length - 1)) : List.of(),
            tokens[tokens.length - 1]
        );
    }

    public static FullyQualifiedName of(Class<?> clazz) {
        if (clazz.isLocalClass()) {
            throw new IllegalArgumentException("Cannot get fully qualified name of local class " + clazz.getSimpleName());
        }
        var typeName = clazz.getTypeName();
        if (typeName.startsWith(clazz.getPackageName())) {
            typeName = typeName.substring(clazz.getPackageName().length() + 1);
        }
        return FullyQualifiedName.of(clazz.getPackageName(), typeName);
    }

    private static String validateType(String type) {
        if (!isValidIdentifier(type)) {
            throw new IllegalArgumentException("Illegal type name: " + type);
        }
        return type;
    }

    private static String validatePackage(String packageName) {
        if (packageName.isEmpty()) return packageName;
        String[] parts = packageName.split("\\.");
        for (String part : parts) {
            if (!isValidIdentifier(part)) {
                throw new IllegalArgumentException("Invalid package name: " + packageName);
            }
        }
        return packageName;
    }

    private static boolean isValidIdentifier(String identifier) {
        if (identifier.isEmpty()) {
            return false;
        }
        if (RESERVED_KEYWORDS.contains(identifier)) {
            return false;
        }
        char firstChar = identifier.charAt(0);
        if (!Character.isJavaIdentifierStart(firstChar)) {
            return false;
        }
        for (int i = 1; i < identifier.length(); i++) {
            if (!Character.isJavaIdentifierPart(identifier.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
