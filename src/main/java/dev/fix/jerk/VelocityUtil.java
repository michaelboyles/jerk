package dev.fix.jerk;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.directive.Directive;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/// Opinionated factory for [VelocityEngine]s which are used to generate Java sources. These are just offered for
/// convenience. There is no requirement that the Velocity engine used with Jerk was instantiated from this factory.
public final class VelocityUtil {
    private VelocityUtil() {
        throw new UnsupportedOperationException();
    }

    /// Create a new Velocity engine with no custom directives.
    public static VelocityEngine newEngine() {
        return new VelocityEngine(commonProperties());
    }

    /// Create a new Velocity engine with all the built-in Jerk directives. Currently:
    /// - [JavaImportsDirective]
    /// - [JavaArgsDirective]
    public static VelocityEngine newEngineWithDefaultDirectives() {
        var props = commonProperties();
        props.setProperty(Velocity.CUSTOM_DIRECTIVES, stringifyDirectives(
            List.of(JavaImportsDirective.class, JavaArgsDirective.class)
        ));
        return new VelocityEngine(props);
    }

    /// Create a new Velocity engine with the given set of directives.
    @SafeVarargs
    public static VelocityEngine newEngineWithDirectives(Class<? extends Directive>... directives) {
        var props = commonProperties();
        if (directives.length > 0) {
            props.setProperty(Velocity.CUSTOM_DIRECTIVES, stringifyDirectives(Arrays.asList(directives)));
        }
        return new VelocityEngine(props);
    }

    private static Properties commonProperties() {
        Properties props = new Properties();
        props.setProperty(Velocity.SPACE_GOBBLING, "lines");
        props.setProperty(Velocity.RESOURCE_LOADERS, "class");
        props.setProperty(Velocity.RUNTIME_REFERENCES_STRICT, "true");
        props.setProperty(
            "resource.loader.class.class",
            org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader.class.getCanonicalName()
        );
        return props;
    }

    private static String stringifyDirectives(List<Class<? extends Directive>> directives) {
        return directives.stream().map(Class::getCanonicalName).collect(Collectors.joining(","));
    }
}
