plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

dependencies {
    implementation(project(":core-mit"))
    implementation(project(":reflection"))

    implementation("org.minidns:minidns-hla:0.3.2")
    implementation("io.ktor:ktor-serialization-gson:3.1.3")
}