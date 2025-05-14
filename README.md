[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/michaelboyles/jerk/build.yml?branch=develop)](https://github.com/michaelboyles/jerk/actions)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/michaelboyles/jerk?sort=semver)](https://github.com/michaelboyles/jerk/releases)
[![GitHub](https://img.shields.io/github/license/michaelboyles/jerk)](https://github.com/michaelboyles/jerk/blob/develop/LICENSE)

Jerk is a set of utils for generating Java source code using [Apache Velocity](https://velocity.apache.org/index.html).

I used to use [JavaPoet](https://github.com/palantir/javapoet) for generating Java, but I found the extra indirection
harder to reason about. Velocity is nice because the template looks much closer to what you're generating. IntelliJ can
do syntax highlighting on the template too.

The main missing feature to make Velocity viable for Java codegen is binding type names and avoiding name clashes. You
can't naively import both `com.foo.Foo` and `com.bar.Foo`.

Jerk adds utils that automatically manage imports and a few other bits.

```xml
<dependency>
    <groupId>io.github.michaelboyles</groupId>
    <artifactId>jerk</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Sample usage

For a good starting point, check
[`io.github.michaelboyles.JavaCodeGenTest`](https://github.com/michaelboyles/jerk/blob/develop/src/test/java/io/github/michaeboyles/JavaCodeGenTest.java)

TLDR:
 - wrap imports that are always needed in the `#imports` directive
   - You'll need to register that directive with the Velocity engine. `VelocityUtil` can give you an engine with the
     directives already registered
 - for imports which are conditionally needed, use subtypes of `io.github.michaelboyles.JavaType`

```java
import io.github.michaelboyles.JavaClass;
import io.github.michaelboyles.JavaContext;
import io.github.michaelboyles.VelocityUtil;

import java.io.StringWriter;

class Sample {
    public static void main(String[] args) throws Exception {
        var engine = VelocityUtil.newEngineWithDefaultDirectives();
        var template = engine.getTemplate("sample.java.vm");
        var context = new JavaContext("org.example");
        context.put("MyArrayList", JavaClass.of(io.github.michaelboyles.test.ArrayList.class));
        StringWriter writer = new StringWriter();
        context.write(template, writer);
        System.out.print(writer);
    }
}
```

Template:

```java
#imports()
import java.util.ArrayList;
#end

class Generated {
    private final ArrayList<String> list1 = new ArrayList<>();
    private final ${MyArrayList}<String> list2 = new ${MyArrayList}<>();
}
```

Output

```java
package org.example;

import java.util.ArrayList;

class Generated {
    private final ArrayList<String> list1 = new ArrayList<>();
    private final io.github.michaelboyles.test.ArrayList<String> list2 = new io.github.michaelboyles.test.ArrayList<>();
}
```
