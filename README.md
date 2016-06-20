# Koolbelt

A bunch of useful extensions for Kotlin.

### Documentation

What's that...? No but seriously, the best way to figure out what's in here is probably to read the unit-tests. I try to keep this module as well-tested as possible. There's a test for most of the methods in the `core` artifact (but not for Android... I don't really have the time to deal with writing those unit-tests).

I add functions to this as I find that I need them in my day-to-day life. They might as well be easily imported into every project.

### Installing

This is hosted on Jitpack. Replace all instances of `VERSION_NUM` with the commit-hash that you would like to add to your project. Or, you can replace it with `master-SNAPSHOT` to constantly stay up to date. However, keep in mind that this makes your builds non-deterministic!

For example, you'd use `compile 'com.github.kevinmost.koolbelt:core:cd260ce'` to pull down the state of this library at [the commit with commit-hash cd260ce](https://github.com/kevinmost/koolbelt/tree/cd260ce).

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


### Extensions to the extensions

There are other modules you can import besides just `core`. Their artifactIds are:

- `extensions-android`: For Android users. NOTE: No unit-test coverage for these. :( PRs welcome!
- `extensions-java8`: For Java 8 specific stuff, so that `core` can continue to serve users on Java 7
