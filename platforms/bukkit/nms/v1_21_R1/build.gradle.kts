apply(plugin = "io.papermc.paperweight.userdev")

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(project(":platforms:bukkit:common"))
    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.21.4-R0.1-SNAPSHOT")
    implementation("xyz.jpenilla", "reflection-remapper", Versions.Bukkit.reflectionRemapper)
}

tasks {
    assemble {
        dependsOn("reobfJar")
    }
}
