apply(plugin = "io.papermc.paperweight.userdev")

repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    api(project(":platforms:bukkit:common"))
    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")
    implementation("xyz.jpenilla", "reflection-remapper", Versions.Bukkit.reflectionRemapper)
}

tasks {
    assemble {
        dependsOn("reobfJar")
    }
}
