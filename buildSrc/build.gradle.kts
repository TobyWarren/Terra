plugins {
    `kotlin-dsl`
    kotlin("jvm") version embeddedKotlinVersion
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.codemc.org/repository/maven-public") {
        name = "CodeMC"
    }
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "PaperMC"
    }
}

dependencies {
    //TODO Allow pulling from Versions.kt
    implementation("com.gradleup.shadow", "shadow-gradle-plugin", "8.3.1")
    //implementation("io.papermc.paperweight", "io.papermc.paperweight.userdev.gradle.plugin", "2.0.0-beta.16")
    implementation("io.papermc.paperweight.userdev:io.papermc.paperweight.userdev.gradle.plugin:2.0.0-beta.16")
    implementation("org.ow2.asm", "asm", "9.7")
    implementation("org.ow2.asm", "asm-tree", "9.7")
    implementation("com.dfsek.tectonic", "common", "4.2.1")
    implementation("org.yaml", "snakeyaml", "2.3")
}