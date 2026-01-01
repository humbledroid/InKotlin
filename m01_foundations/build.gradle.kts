plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("io.github.serpro69:kotlin-faker:1.15.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
}

kotlin {
    jvmToolchain(21)
}
