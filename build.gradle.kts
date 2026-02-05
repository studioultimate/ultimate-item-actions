plugins {
    id("java")
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.bukkitYaml)
}

group = "com.gabriaum.ultimate.itemactions"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    compileOnly(
        files(
            "./libs/spigot-1.8.8.jar"
        )
    )

    implementation("de.tr7zw:item-nbt-api:2.15.5")
}

tasks.shadowJar {
    relocate(
        "de.tr7zw.changeme.nbtapi",
        "com.gabriaum.ultimate.itemactions.infra.util.nbtapi"
    )
}