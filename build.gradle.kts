import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform") version "1.9.10"
    id("org.jetbrains.dokka") version "1.9.0"
    `maven-publish`
    signing
}

version = "24.0.1"
group = "com.sschr15.annotations"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js {
        nodejs()
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasm()

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    linuxArm64()
    linuxX64()

    mingwX64()

    ios()
    tvos()
    watchos()
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    tvosSimulatorArm64()
    watchosSimulatorArm64()
}

val dokkaJar = tasks.create<Jar>("dokkaJar") {
    from(tasks.dokkaHtml)
    archiveClassifier = "javadoc"
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}

publishing {
    if (System.getenv("MAVEN_URL") != null) {
        repositories {
            maven(System.getenv("MAVEN_URL")!!) {
                if (System.getenv("MAVEN_USER") != null) {
                    credentials {
                        username = System.getenv("MAVEN_USER")
                        password = System.getenv("MAVEN_PASS")
                    }
                }
            }
        }
    } else {
        repositories {
            mavenLocal()
        }
    }

    publications {
        withType<MavenPublication> {
            artifact(dokkaJar) {
                classifier = "javadoc"
            }

            pom {
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }

                name = "JB Annotations - Multiplatform Port"
                description = "A port of Jetbrains Java annotations to Kotlin/Multiplatform, " +
                        "for those sweet annotations everywhere"
                url = "https://github.com/sschr15/jetbrains-annotations-kmp"

                developers {
                    developer {
                        name = "sschr15"
                        email = "me@sschr15.com"
                        url = "https://github.com/sschr15"
                        timezone = "America/Chicago"
                    }
                }

                scm {
                    connection = this@pom.url.get().replace("https", "scm:git:git")
                    developerConnection = connection.get().replace("git://", "ssh://")
                    url = this@pom.url
                }
            }
        }
    }
}
