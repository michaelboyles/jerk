package dev.fix.jerk;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/// A safe way of referring to static methods. Lazily binds the type name so it can avoid collisions. Reflection is
/// used to verify that the method exists for the given signature.
///
/// An instance can be bound to a given set of arguments to create a call.
public interface JavaStaticMethodRef {
    /// Bind the given arguments to create a call expression in the form `MyClass.myMethod(arg1, arg2)`.
    JavaExpression createCallExpression(JavaExpression... args);

    static JavaStaticMethodRef of(Class<?> clazz, String name, Class<?>... args) {
        Method method;
        try {
            method = clazz.getDeclaredMethod(name, args);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No such method", e);
        }
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new IllegalArgumentException("Method is not static");
        }
        return new ReflectiveMethodRef(clazz, method);
    }
}

final class ReflectiveMethodRef implements JavaStaticMethodRef {
    private final Class<?> clazz;
    private final Method method;

    public ReflectiveMethodRef(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    @Override
    public JavaExpression createCallExpression(JavaExpression... args) {
        var argList = new ArgumentFragment(Arrays.asList(args));
        return TemplateJavaExpression.of("%s.%s(%s)").bind(clazz, method.getName(), argList);
    }
}