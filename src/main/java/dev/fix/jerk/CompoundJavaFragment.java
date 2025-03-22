package dev.fix.jerk;

import com.google.errorprone.annotations.CheckReturnValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

final class CompoundJavaFragment implements JavaFragment {
    private final Collection<JavaFragment> blocks;

    private CompoundJavaFragment(Collection<JavaFragment> blocks) {
        this.blocks = new ArrayList<>(blocks);
    }

    @Override
    public String toString() {
        return blocks.stream()
            .map(JavaFragment::toString)
            .collect(Collectors.joining("\n"));
    }

    @CheckReturnValue
    static JavaFragment of(Collection<JavaFragment> blocks) {
        if (blocks.size() == 1) return blocks.stream().findFirst().orElseThrow();
        return new CompoundJavaFragment(blocks);
    }
}
