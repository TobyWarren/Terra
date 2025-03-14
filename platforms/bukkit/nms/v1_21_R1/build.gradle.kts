apply(plugin = "io.papermc.paperweight.userdev")

repositories {
    maven("https://repo.papermc.io/service/rest/repository/browse/maven-public/")
}

dependencies {
    api(project(":platforms:bukkit:common"))
    paperDevBundle(Versions.Bukkit.paperDevBundle)
    implementation("xyz.jpenilla", "reflection-remapper", Versions.Bukkit.reflectionRemapper)
}

tasks {
    assemble {
        dependsOn("reobfJar")
    }
}
