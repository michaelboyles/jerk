package dev.fix.jerk;

import com.google.errorprone.annotations.CheckReturnValue;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class JavaMethod implements JavaFragment {
    private final List<Modifier> modifiers;
    private final JavaType returnType;
    private final List<JavaParam> parameters;
    private final String name;
    private final JavaFragment body;

    private JavaMethod(List<Modifier> modifiers, JavaType returnType, List<JavaParam> parameters, String name, JavaFragment body) {
        this.modifiers = modifiers;
        this.returnType = returnType;
        this.parameters = parameters;
        this.name = name;
        this.body = body;
    }

    @Override
    public String toString() {
        String modifiers = this.modifiers.stream()
            .map(Modifier::name)
            .map(String::toLowerCase)
            .collect(Collectors.joining(" "));
        String params = this.parameters.stream()
            .map(JavaParam::toString)
            .collect(Collectors.joining(", "));
        String returnType = this.returnType == null ? "void" : this.returnType.toString();
        return TemplateJavaFragment.of("""
            %s %s %s(%s) {
                %s
            }
            """,
            modifiers, returnType, name, params, body
        ).toString();
    }

    public static Builder builderForName(String name) {
        return new Builder(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JavaMethod other)) return false;
        if (!Objects.equals(this.modifiers, other.modifiers)) return false;
        if (!Objects.equals(this.returnType, other.returnType)) return false;
        if (!Objects.equals(this.parameters, other.parameters)) return false;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.modifiers, this.returnType, this.parameters, this.name);
    }

    public static class Builder {
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<JavaParam> parameters = new ArrayList<>();
        private final String name;
        private JavaType returnType;
        private JavaFragment body = JavaFragment.empty();

        private Builder(String name) {
            this.name = name;
        }

        public Builder withModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        public Builder withReturnType(JavaType returnType) {
            this.returnType = returnType;
            return this;
        }

        public Builder withParameter(JavaParam parameter) {
            this.parameters.add(parameter);
            return this;
        }

        public Builder withBody(JavaFragment body) {
            this.body = body;
            return this;
        }

        @CheckReturnValue
        public JavaMethod build() {
            return new JavaMethod(modifiers, returnType, parameters, name, body);
        }
    }
}
