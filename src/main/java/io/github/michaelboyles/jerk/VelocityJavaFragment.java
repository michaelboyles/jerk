package io.github.michaelboyles.jerk;

import org.apache.velocity.Template;

import java.io.StringWriter;

public final class VelocityJavaFragment implements JavaFragment {
    private final Template template;

    private VelocityJavaFragment(Template template) {
        this.template = template;
    }

    public static VelocityJavaFragment ofTemplate(Template template) {
        return new VelocityJavaFragment(template);
    }

    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        var context = JavaContext.current()
            .orElseThrow(() -> new IllegalStateException("VelocityJavaFragment must be rendered in a JavaContext"));
        template.merge(context, writer);
        return writer.toString();
    }
}
