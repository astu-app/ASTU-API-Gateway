plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

val ktor_version: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")
    implementation(project(":account-data-api"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
