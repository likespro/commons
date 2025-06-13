plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

dependencies {
    api(project(":core-mit"))

    implementation("io.ktor:ktor-serialization-gson:3.1.3")
}