import com.dfsek.terra.configureCommon
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.modrinth.minotaur.TaskModrinthUpload
import net.fabricmc.loom.LoomGradleExtension
import net.fabricmc.loom.task.RemapJarTask

plugins {
    `java-library`
    id("fabric-loom").version("0.6-SNAPSHOT")
    id("com.modrinth.minotaur").version("1.1.0")
}

configureCommon()

tasks.named<ShadowJar>("shadowJar") {
    relocate("org.json", "com.dfsek.terra.lib.json")
    relocate("org.yaml", "com.dfsek.terra.lib.yaml")
}

group = "com.dfsek.terra.fabric"

dependencies {
    "shadedApi"(project(":common"))
    "shadedImplementation"("org.yaml:snakeyaml:1.27")
    "shadedImplementation"("com.googlecode.json-simple:json-simple:1.1.1")

    "minecraft"("com.mojang:minecraft:1.16.5")
    "mappings"("net.fabricmc:yarn:1.16.5+build.5:v2")
    "modImplementation"("net.fabricmc:fabric-loader:0.11.2")

    "modImplementation"("net.fabricmc.fabric-api:fabric-api:0.31.0+1.16")
}

tasks.named<ShadowJar>("shadowJar") {
    relocate("org.json", "com.dfsek.terra.lib.json")
    relocate("org.yaml", "com.dfsek.terra.lib.yaml")
}


configure<LoomGradleExtension> {
    accessWidener("src/main/resources/terra.accesswidener")
}

val remapped = tasks.register<RemapJarTask>("remapShadedJar") {
    group = "fabric"
    val shadowJar = tasks.getByName<ShadowJar>("shadowJar")
    dependsOn(shadowJar)
    input.set(shadowJar.archiveFile)
    archiveFileName.set(shadowJar.archiveFileName.get().replace(Regex("-shaded\\.jar$"), "-shaded-mapped.jar"))
    addNestedDependencies.set(true)
    remapAccessWidener.set(true)
}


tasks.register<TaskModrinthUpload>("publishModrinth") {
    dependsOn("remapShadedJar")
    group = "fabric"
    token = System.getenv("MODRINTH_SECRET")
    projectId = "FIlZB9L0"
    versionNumber = project.version.toString()
    uploadFile = remapped.get().archiveFile.get().asFile
    addGameVersion("1.16.4")
    addGameVersion("1.16.5")
    addLoader("fabric")
}