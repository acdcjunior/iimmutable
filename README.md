# java6-functional-immutable

Simple immutable classes to support functional programming in Java 6.

## Why?

I wanted a simple lib containing lists with `.map` and `.filter` methods. While there are
already many around, I had some specific requirements: 

- The names of the classes should be different from the `java.util` ones to better enable simultaneous usage.
    - E.g., here I use `IList` as class name to differentiate from `java.util.List` and hint that the list is immutable.
    - This is to cause less confusion for inexperienced developers in old codebases.
- Inclusion of more general classes such as `IPair`, `ITriple` and `IOption`.
    - Also other more particular to the functional style, such as `IEither` and the `IFunction` interface.
- Addition of interfaces, classes and methods as needed.

Lastly, it seemed like a good exercise anyway.

LICENSE: MIT