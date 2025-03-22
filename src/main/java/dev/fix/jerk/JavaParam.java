package dev.fix.jerk;

public record JavaParam(JavaType type, String name) {
    @Override
    public String toString() {
        return type + " " + name;
    }
}
