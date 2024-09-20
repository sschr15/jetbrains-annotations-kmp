import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform") version "2.0.0"
    id("org.jetbrains.dokka") version "1.9.20"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    `maven-publish`
    signing
}

version = "24.1.0+apple"
group = "com.sschr15.annotations"

repositories {
    mavenCentral()
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()
    js {
        nodejs()
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        nodejs()
        browser()
        d8()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmWasi {
        nodejs()
    }

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    linuxArm64()
    linuxX64()

    mingwX64()

    iosX64()
    iosArm64()
    macosX64()
    macosArm64()
    tvosX64()
    tvosArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    watchosX64()
    iosSimulatorArm64()
    tvosSimulatorArm64()
    watchosSimulatorArm64()
    watchosDeviceArm64()
}

val dokkaJar = tasks.create<Jar>("dokkaJar") {
    from(tasks.dokkaHtml)
    archiveClassifier = "javadoc"
}

signing {
    val signingKey = System.getenv("SIGNING_KEY")
    val signingPassword = System.getenv("SIGNING_PASSWORD")
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}

// why must they make me suffer this way
tasks {
    withType<PublishToMavenRepository> {
        mustRunAfter(withType<Sign>())
    }
}

publishing {
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

if ("oss.sonatype.org" in (System.getenv("MAVEN_URL") ?: "")) {
    nexusPublishing {
        this.repositories {
            sonatype {
                username = System.getenv("MAVEN_USER")
                password = System.getenv("MAVEN_PASS")

                nexusUrl = uri(System.getenv("MAVEN_URL"))
            }
        }
    }
}
