package io.github.michaelboyles.jerk;

import io.github.michaelboyles.jerk.test.UniqueName;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.michaelboyles.jerk.JavaExpression.booleanLiteral;
import static io.github.michaelboyles.jerk.JavaExpression.intLiteral;
import static io.github.michaelboyles.jerk.JavaExpression.longLiteral;
import static io.github.michaelboyles.jerk.JavaExpression.nullLiteral;
import static io.github.michaelboyles.jerk.JavaExpression.stringLiteral;
import static java.nio.file.Files.readString;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class JavaCodeGenTest {
    private static final JavaStaticMethodRef HASH_METHOD = JavaStaticMethodRef.of(Objects.class, "hash", Object[].class);

    @Test void generateJava() throws Exception {
        var engine = VelocityUtil.newEngineWithDefaultDirectives();

        var template = engine.getTemplate("JavaCodeGen.java.vm");
        JavaContext context = new JavaContext("org.example");
        context.put("MyArrayList", JavaClass.of(io.github.michaelboyles.jerk.test.ArrayList.class));
        context.put("UniqueName", JavaClass.of(UniqueName.class));
        context.put("ListOfList", JavaClass.of(ArrayList.class)
            .withParam(JavaClass.of(io.github.michaelboyles.jerk.test.ArrayList.class))
        );
        context.put("arg1", stringLiteral("te\"st"));
        context.put("arg2", stringLiteral("test2"));

        var main = JavaMethod.builderForName("main")
            .withModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .withParameter(JavaParam.of(String.class, "args"))
            .withBody(
                JavaFragment.join(List.of(
                    TemplateJavaExpression.of("var list = new %s<%s>()").bind(ArrayList.class, String.class).asStatement(),
                    AccessExpression.accessLocal("list").call("clear").asStatement(),
                    JavaStatement.unchecked("System.out.println(\"Hello\")"),
                    HASH_METHOD.createCallExpression(
                        booleanLiteral(true),
                        intLiteral(-10),
                        longLiteral(9999999999999L),
                        nullLiteral(),
                        JavaExpression.unchecked("null")
                    ).asStatement()
                ))
            )
            .build();
        context.put("main", main);

        var testMethod = VelocityJavaFragment.ofTemplate(engine.getTemplate("testMethod.java.vm"));
        context.put("testMethod", testMethod);

        StringWriter writer = new StringWriter();
        context.write(template, writer);

        var expectedUri = requireNonNull(JavaCodeGenTest.class.getClassLoader().getResource("JavaCodeGen.expected.java")).toURI();
        var expected = readString(Paths.get(expectedUri)).replace("\r\n", "\n");
        var actual = writer.toString().replace("\r\n", "\n");

        assertEquals(expected, actual);
    }
}
