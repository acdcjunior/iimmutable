# java6-functional-immutable

Simple immutable DA classes to support functional programming in Java 6.

## Why?

I wanted a simple lib containing lists with `.map` and `.filter` methods. While there are
already many around, I had some specific requirements: 

- The names of the classes should be different from the `java.util` ones.
    - E.g., here I use `IList` as class name to differentiate from `java.util.List` and hint that the list is immutable.
    - This is to cause less confusion for inexperienced developers in old codebases.
- Inclusion of more general classes such as `IPair`, `ITriple` and `IOption`.
- Addition of classes and methods as needed.

Lastly, it seemed like a good exercise anyway.

LICENSE: MIT