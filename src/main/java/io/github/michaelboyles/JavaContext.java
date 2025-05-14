package io.github.michaelboyles;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/// Extends a [VelocityContext] to add support for Java. It allows you to avoid name-clashes when importing classes
/// with the same type name from different packages.
///
/// You should create a new JavaContext for each Java source file you generate because they're stateful.
///
/// A JavaContext is created targeting a specific package. It then knows that classes in the same package don't require
/// an import statement.
///
/// A JavaContext works in tandem with the subtypes of [JavaType], which are aware of the context and will register
/// themselves as they're used. It's supported the lazy bindings of the subtypes of [JavaFragment].
public final class JavaContext extends VelocityContext {
    private static final ThreadLocal<JavaContext> current = new ThreadLocal<>();

    public static Optional<JavaContext> current() {
        return Optional.ofNullable(current.get());
    }

    private final Set<JavaClass> imports = new HashSet<>();
    private final Map<JavaClass, String> typeToReferencedName = new HashMap<>();
    private final String packageName;

    public JavaContext(String packageName) {
        this.packageName = packageName;
    }

    public void addConstantImport(String fqn) {
        getOrCreateReference(new JavaClass(fqn));
    }

    public String getOrCreateReference(JavaClass clazz) {
        String referencedName = typeToReferencedName.get(clazz);
        if (referencedName != null) return referencedName;

        // Name clash. There already is something using the simple name, so use the canonical name
        if (typeToReferencedName.containsValue(clazz.simpleName())) {
            if (clazz.packageName().isEmpty()) {
                throw new IllegalStateException("Import clash between for class in default package: " + clazz.simpleName());
            }
            var canonicalName = clazz.canonicalName();
            typeToReferencedName.put(clazz, canonicalName);
            return canonicalName;
        }
        // First use of this type, and simple name doesn't clash
        typeToReferencedName.put(clazz, clazz.simpleName());
        if (!clazz.isDefaultImported() && !clazz.isInPackage(packageName)) {
            imports.add(clazz);
        }
        return clazz.simpleName();
    }

    private String formattedImports() {
        return imports.stream()
            .map(JavaClass::canonicalName)
            .sorted()
            .map(fqn -> "import " + fqn + ";")
            .collect(Collectors.joining(System.lineSeparator()));
    }

    public void write(Template template, Writer writer) throws IOException {
        try {
            current.set(this);
            StringWriter tempBuffer = new StringWriter();
            template.merge(this, tempBuffer);

            writer.write("package ");
            writer.write(packageName);
            writer.write(';');
            writer.write(System.lineSeparator());
            writer.write(System.lineSeparator());
            String imports = formattedImports();
            if (!imports.isBlank()) {
                writer.write(imports);
                writer.write(System.lineSeparator());
                writer.write(System.lineSeparator());
            }
            writer.write(tempBuffer.toString().trim());
        }
        finally {
            current.remove();
        }
    }
}
