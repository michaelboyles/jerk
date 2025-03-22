package dev.fix.jerk;

import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.Files.readString;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class JavaCodeGenTest {
    @Test void generateJava() throws Exception {
        var engine = VelocityUtil.newEngineWithDirectives(
            JavaArgsDirective.class,
            JavaImportsDirective.class
        );

        var template = engine.getTemplate("JavaCodeGen.java.vm");
        JavaContext context = new JavaContext("org.example");
        context.put("MyArrayList", JavaClass.of(dev.fix.jerk.test.ArrayList.class));
        context.put("UniqueName", JavaClass.of(dev.fix.jerk.test.UniqueName.class));

        var main = JavaMethod.builderForName("main")
            .withModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .withParameter(new JavaParam(JavaClass.of(String.class), "args"))
            .withBody(
                TemplateJavaExpression.of("var list = new %s<%s>()").bind(ArrayList.class, String.class).asStatement()
            )
            .build();

        context.put("main", main);

        StringWriter writer = new StringWriter();
        context.write(template, writer);

        var expectedUri = requireNonNull(JavaCodeGenTest.class.getClassLoader().getResource("JavaCodeGen.expected.java")).toURI();
        var expected = readString(Paths.get(expectedUri)).replace("\r\n", "\n");
        var actual = writer.toString().replace("\r\n", "\n");

        assertEquals(expected.length(), actual.length(), "Length should be equal");
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), actual.charAt(i));
        }
    }
}
