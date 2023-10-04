plugins {
    kotlin("multiplatform") version "1.9.10"
    `maven-publish`
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
    }

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    linuxArm64()
    linuxX64()

    mingwX64()

    //TODO Apple native targets
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
}
