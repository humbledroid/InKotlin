plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "com.death"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("io.github.serpro69:kotlin-faker:1.16.0")
}

tasks.test {
    useJUnit()
}

kotlin {
    jvmToolchain(21)
    target {
        compilerOptions {
            freeCompilerArgs.add("-Xmulti-dollar-interpolation")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass.set("MainKt")
}