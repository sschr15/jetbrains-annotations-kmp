# Jetbrains Annotations - Kotlin Multiplatform

A copy of Jetbrains' [java-annotations](https://github.com/jetbrains/java-annotations) library,
converted in-place (mostly) by the Jetbrains kotlin IDEA plugin's "Convert Java class to Kotlin file" utility.

**This is not an official Jetbrains resource**, but instead is a modified copy to support Kotlin Multiplatform targets.

## Usage

This is published to Maven Central as `com.sschr15.annotations:jb-annotations-kmp:<version>`.
It is available for every non-deprecated KMP target.

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
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.sschr15.annotations:jb-annotations-kmp:$VERSION")
            }
        }
    }
}
```
