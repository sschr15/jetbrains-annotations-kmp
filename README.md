# [Jetbrains Annotations](https://github.com/JetBrains/java-annotations) now officially supports Kotlin Multiplatform

Using the normal Maven coordinates for Jetbrains' official annotations will work in KMP now.

Old readme for history:

---

# Jetbrains Annotations - Kotlin Multiplatform

A copy of Jetbrains' [java-annotations](https://github.com/jetbrains/java-annotations) library,
converted in-place (mostly) by the Jetbrains kotlin IDEA plugin's "Convert Java class to Kotlin file" utility.

**This is not an official Jetbrains resource**, but instead is a modified copy to support Kotlin Multiplatform targets.

## Usage

This is published to Maven Central as `com.sschr15.annotations:jb-annotations-kmp:<version>`.
It is meant to be available for every non-deprecated KMP target (links to [non-native](https://kotlinlang.org/docs/multiplatform-dsl-reference.html#targets) and [native](https://kotlinlang.org/docs/native-target-support.html) targets). If any are missing, go ahead and [create an issue](https://github.com/sschr15/jetbrains-annotations-kmp/issues/new) to let me know.

Versioning matches that of the upstream annotations, with any rebuilds having specific `+something` information added.

The latest version (matching that of the original library) is shown on the right as the latest GitHub release.
The first version ported is `24.0.1`.

Using Groovy-style Gradle:

```groovy
kotlin {
    sourceSets.commonMain {
        dependencies {
            implementation "com.sschr15.annotations:jb-annotations-kmp:$VERSION"
        }
    }
}
```

Using Kotlin-style Gradle:

```kotlin
kotlin {
    sourceSets["commonMain"] {
        dependencies {
            implementation("com.sschr15.annotations:jb-annotations-kmp:$VERSION")
        }
    }
}
```
