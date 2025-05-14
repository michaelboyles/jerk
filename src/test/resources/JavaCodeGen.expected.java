package org.example;

import io.github.michaelboyles.jerk.test.ImportedName;
import io.github.michaelboyles.jerk.test.UniqueName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

final class JavaCodeGen {
    private final ArrayList<String> list1 = new ArrayList<>();
    private final io.github.michaelboyles.jerk.test.ArrayList list2 = new io.github.michaelboyles.jerk.test.ArrayList();
    private final ArrayList<io.github.michaelboyles.jerk.test.ArrayList> list3 = new ArrayList<io.github.michaelboyles.jerk.test.ArrayList>();
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

    public static ImportedName test() {
        return new ImportedName();
    }
}