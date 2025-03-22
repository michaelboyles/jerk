package dev.fix.jerk;

/// The primitive types within Java, such as "int"
public enum JavaPrimitiveType implements JavaType {
    BYTE("0"),
    SHORT("0"),
    INT("0"),
    LONG("0"),
    FLOAT("0"),
    DOUBLE("0"),
    BOOLEAN("false"),
    CHAR("0");

    private final String defaultLiteral;

    JavaPrimitiveType(String defaultLiteral) {
        this.defaultLiteral = defaultLiteral;
    }

    @Override
    public JavaClass box() {
        return switch (this) {
            case BYTE    -> JavaClass.of(Byte.class);
            case SHORT   -> JavaClass.of(Short.class);
            case INT     -> JavaClass.of(Integer.class);
            case LONG    -> JavaClass.of(Long.class);
            case FLOAT   -> JavaClass.of(Float.class);
            case DOUBLE  -> JavaClass.of(Double.class);
            case BOOLEAN -> JavaClass.of(Boolean.class);
            case CHAR    -> JavaClass.of(Character.class);
        };
    }

    @Override
    public String canonicalName() {
        return toString();
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public String defaultLiteral() {
        return defaultLiteral;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
