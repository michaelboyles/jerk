package org.example;

import dev.fix.jerk.test.ImportedName;
import dev.fix.jerk.test.UniqueName;
import java.util.ArrayList;

final class JavaCodeGen {
    private final ArrayList<String> list1 = new ArrayList<>();
    private final dev.fix.jerk.test.ArrayList list2 = new dev.fix.jerk.test.ArrayList();
    private final UniqueName unique = new UniqueName();

    public static void test1() {
        ImportedName importedName = new ImportedName();
    }

    public static void main(String args) {
        var list = new ArrayList<String>();
    }
}