package io.github.michaelboyles;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.ASTText;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// A Velocity directive that marks imports which are always included. The main goal is to remove the possibility of name
/// clashes for types with the same "simple name". The directive removes the imports from the actual Velocity template
/// pass, but they're re-added later by [JavaContext].
///
/// This block must always occur before any dynamic imports, else the dynamic import may be assigned a simple name which
/// is required by a non-dynamic import.
public final class JavaImportsDirective extends Directive {
    private static final Pattern IMPORT_PATTERN = Pattern.compile("\\s*import\\s+([a-zA-Z0-9.]+)\\s*;\\s*");

    @Override
    public String getName() {
        return "imports";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node)
        throws ResourceNotFoundException, ParseErrorException, MethodInvocationException
    {
        JavaContext.current().ifPresent(ctx -> {
            Node child = node.jjtGetChild(0);
            if (child == null) return;
            Node nestedChild = child.jjtGetChild(0);
            if (nestedChild == null) return;
            if (nestedChild instanceof ASTText text) {
                String content = text.getCtext();
                String[] lines = content.split("\n");
                for (String line : lines) {
                    if (line.isBlank()) continue;
                    Matcher matcher = IMPORT_PATTERN.matcher(line);
                    if (!matcher.matches()) {
                        throw new RuntimeException("Not an import: '" + line + '\'');
                    }
                    String fullyQualifiedName = matcher.group(1);
                    ctx.addConstantImport(fullyQualifiedName);
                }
            }
        });
        return true;
    }
}
