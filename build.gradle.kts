plugins {
    java
    `java-library`
    application
    kotlin("jvm") version "1.9.10"
    kotlin("kapt") version "1.9.10"
}

group = "me.topilov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("io.ktor:ktor-client-core:1.5.0")
    implementation("io.ktor:ktor-client-cio:1.5.0")
    implementation("com.google.dagger:dagger:2.48")
    kapt("com.google.dagger:dagger-compiler:2.48")
    implementation("org.slf4j:slf4j-api:1.7.32")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

kapt {
    arguments {
        arg("kapt.verbose", "true")
        arg("kapt.use.worker.api", "false")
        arg("kapt.incremental.apt", "false")
    }
}