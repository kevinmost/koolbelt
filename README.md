# Koolbelt

A bunch of useful extensions for Kotlin.

### Documentation

What's that...? No but seriously, the best way to figure out what's in here is probably to read the unit-tests. I try to keep this module as well-tested as possible. There's a test for most of the methods in the `core` artifact (but not for Android... I don't really have the time to deal with writing those unit-tests).

I add functions to this as I find that I need them in my day-to-day life. They might as well be easily imported into every project.

### Installing

This is hosted on Jitpack. Replace all instances of `VERSION_NUM` with the commit-hash that you would like to add to your project. Or, you can replace it with `master-SNAPSHOT` to constantly stay up to date. However, keep in mind that this makes your builds non-deterministic!

For example, you'd use `compile 'com.github.kevinmost.koolbelt:core:cd260ce'` to pull down the state of this library at [the commit with commit-hash cd260ce](https://github.com/kevinmost/koolbelt/tree/cd260ce).

Aside from `core`, there are other modules for extensions for (at the moment) Android and Java 8. More modules will come in the future.

#### For Gradle

Add `jitpack.io` to your repositories (in your root build.gradle, add the following line to the end of your `repositories { ... }` block):

```groovy
maven { url "https://jitpack.io" }
```

Then add the client to your dependencies

```groovy
compile 'com.github.kevinmost.koolbelt:core:VERSION_NUM'
```

---

#### For Maven

Add `jitpack.io` to your repositories:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Then add the client to your dependencies:

```xml
<dependency>
  <groupId>com.github.kevinmost.koolbelt</groupId>
  <artifactId>core</artifactId>
  <version>VERSION_NUM</version>
</dependency>
```

---

---

---


| Module name          | Method count | Description                              | Depends on |
|----------------------|--------------|------------------------------------------|------------|
| `core`               | <a href="http://www.methodscount.com/?lib=com.github.kevinmost.koolbelt%3Acore%3Ae35b4b8"><img src="https://img.shields.io/badge/Methods count-560-e91e63.svg"/></a> | Core functionality that works on Java 7+ |            |
| `extensions-android` | <a href="http://www.methodscount.com/?lib=com.github.kevinmost.koolbelt%3Aextensions-android%3Ae35b4b8"><img src="https://img.shields.io/badge/Methods count-339-e91e63.svg"/></a> | Android-specific functionality           | `core`     |
| `extensions-java8`   | <a href="http://www.methodscount.com/?lib=com.github.kevinmost.koolbelt%3Aextensions-java8%3Ae35b4b8"><img src="https://img.shields.io/badge/Methods count-2-e91e63.svg"/></a> | Java 8-specific methods                  | `core`     |
