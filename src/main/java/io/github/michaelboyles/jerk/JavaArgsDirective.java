package io.github.michaelboyles.jerk;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/// A Velocity directive to construct an argument list for a function. It basically just comma-separates every line and
/// ignores whitespace.
public final class JavaArgsDirective extends Directive {
    @Override
    public String getName() {
        return "args";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
        throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {

        Node block = node.jjtGetChild(0);
        if (block == null) return false;
        int numChildren = block.jjtGetNumChildren();

        List<String> parts = new ArrayList<>();
        for (int i = 0; i < numChildren; ++i) {
            Node child = block.jjtGetChild(i);
            StringWriter tmpWriter = new StringWriter();
            child.render(context, tmpWriter);
            parts.add(tmpWriter.toString());
        }

        writer.write(transform(parts));
        return true;
    }

    private static String transform(List<String> parts) {
        var args = new ArrayList<String>();
        for (String part : parts) {
            String[] lines = part.split("\n");
            for (var line : lines) {
                String trimmedLine = line.strip();
                if (!trimmedLine.isBlank()) args.add(trimmedLine);
            }
        }
        return String.join(", " , args);
    }
}
