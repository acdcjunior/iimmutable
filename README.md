# java6-functional-programming

Simple classes to support functional programming in Java 6.

## Why?

I wanted a simple lib containing lists with `.map` and `.filter` methods. While there are
already many around, I had some specific requirements: 

- The names of the classes should be different from the `java.util` ones.
    - E.g., here I use `FPList` as class name to differentiate from `java.util.List`.
    - This is to cause less confusion for inexperienced developers in old codebases.
- Inclusion of more general classes such as `FPPair`, `FPTriple` and `FPOption`.
- Addition of classes and methods as needed.

Lastly, it seemed like a good exercise anyway.

LICENSE: MIT