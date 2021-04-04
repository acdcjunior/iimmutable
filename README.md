# iimmutable

Yet another lib with simple immutable classes to support functional programming in Java 6+.
One of our main goals is to always support Java 6, working as a lifeboat for those stuck with these versions.

## Examples

```java
IList<String> l = IList.listOf("a", "b", "c");
IPair<Integer, String> p = IPair.pairOf(123, "abc");
ITriple<Integer, String, Long> t = ITriple.tripleOf(123, "abc", 555L);

ISet<String> s = ISet.setOf("a", "b", "c");
IMap<Integer, String> m = IMap.mapOf(pairOf(123, "abc"), pairOf(456, "def"), pairOf(789, "ghi"));

IOption<String> aSome = IOption.some("foo");
IOption<String> aNone = IOption.none();
IEither<Integer, String> aLeft = IEither.left(123);
IEither<Integer, String> aRight = IEither.right("foo");
```

And more!

## Dependency

There are no transitive dependencies other than `org.jetbrains:annotations:13.0` (for `@NotNull` and similar annotations).

Maven:

```xml
<dependency>
    <groupId>dev.acdcjunior</groupId>
    <artifactId>iimmutable</artifactId>
    <version>1.0.0</version>
</dependency>
```
    
Gradle:

```groovy
implementation 'dev.acdcjunior:iimmutable:1.0.0'
```


## Why?

We wanted a simple lib containing lists with `.map` and `.filter` methods. While there are
already many around, we had some specific requirements: 

- The names of the classes should be different from the `java.util` ones to better enable side-by-side usage.
    - E.g., here we use `IList` as class name to differentiate from `java.util.List` and hint that the list is immutable.
    - This is to cause less confusion for inexperienced developers in old codebases.
- Since it is a different lib, it should be easier to add to a legacy project where the classpath is already filled
  with many libs (e.g. guava) which, if you updated, there could be conflicts between your dependencies' dependencies.
  For instance, you may not be able to update Guava version if one dependency you already have depends on a very specific
  Guava version.
- Inclusion of more general classes such as `IPair`, `ITriple` and `IOption`.
    - Also other more particular to the functional style, such as `IEither` and the `IFunction` interface.
- Addition of interfaces, classes and methods as needed.
- Anonymous classes [_can_ get verbose and that is a drawback][guava-functional]. Still, it is possible to
 profit from the functional style once it is enough a common way of reasoning in your team. In this case, the benefits
 may be higher than the cons. Filtering `for`s, as an example, can get much in the way of the intent of the
 code. Nevertheless, imperative foreach loops (`for (X x: myIList) {}`) are still available (e.g. `IList` implements `Iterable`), so
 you always have the choice of falling back when the functional approach is unsuitable.
- This is a great companion to [retrolambda][retrolambda]. This also overcomes the problem of the anonymous classes.

Lastly, it seemed like a good exercise anyway.

LICENSE: MIT

[guava-functional]: https://github.com/google/guava/wiki/FunctionalExplained#Caveats
[retrolambda]: https://github.com/luontola/retrolambda
