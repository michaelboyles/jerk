package org.example;

import dev.fix.jerk.test.ImportedName;
import dev.fix.jerk.test.UniqueName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

final class JavaCodeGen {
    private final ArrayList<String> list1 = new ArrayList<>();
    private final dev.fix.jerk.test.ArrayList list2 = new dev.fix.jerk.test.ArrayList();
    private final ArrayList<dev.fix.jerk.test.ArrayList> list3 = new ArrayList<dev.fix.jerk.test.ArrayList>();
    private final UniqueName unique = new UniqueName();

    public static void test1() {
        ImportedName importedName = new ImportedName();
        Arrays.asList("te\"st", "test2");
    }

    public static void main(String args) {
        var list = new ArrayList<String>();
        list.clear();
        System.out.println("Hello");
        Objects.hash(true, -10, 9999999999999L, null, null);
    }
}