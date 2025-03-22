package dev.fix.jerk;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.directive.Directive;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class VelocityUtil {
    private VelocityUtil() {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    public static VelocityEngine newEngineWithDirectives(Class<? extends Directive>... directives) {
        Properties props = new Properties();
        props.setProperty(Velocity.SPACE_GOBBLING, "lines");
        props.setProperty(Velocity.CUSTOM_DIRECTIVES, stringifyDirectives(Arrays.asList(directives)));
        props.setProperty(Velocity.RESOURCE_LOADERS, "class");
        props.setProperty(Velocity.RUNTIME_REFERENCES_STRICT, "true");
        props.setProperty(
            "resource.loader.class.class",
            org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader.class.getCanonicalName()
        );
        return new VelocityEngine(props);
    }

    private static String stringifyDirectives(List<Class<? extends Directive>> directives) {
        return directives.stream().map(Class::getCanonicalName).collect(Collectors.joining(","));
    }
}
