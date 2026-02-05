import java.io.ByteArrayOutputStream

plugins {
    id("java")
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.bukkitYaml)
    alias(libs.plugins.gitVersion)
}

group = "com.gabriaum.ultimate.itemactions"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
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
    implementation(libs.bukkitCommand)
    implementation(libs.inventoryFramework)
}

tasks.shadowJar {
    relocate(
        "de.tr7zw.changeme.nbtapi",
        "com.gabriaum.ultimate.itemactions.infra.util.nbtapi"
    )
}

fun gitCommand(vararg args: String): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", *args)
        standardOutput = stdout
    }
    return stdout.toString().trim()
}

fun gitHash(): String =
    gitCommand("rev-parse", "--short", "HEAD")

fun gitBranch(): String =
    gitCommand("rev-parse", "--abbrev-ref", "HEAD")

fun getBuildDate(): String =
    gitCommand("show", "--no-patch", "--format=%ci")
        .replace(" -0300", "")
        .replace("-", "/")

bukkit {
    name = "UltimateItemActions"
    main = "com.gabriaum.ultimate.itemactions.UltimateItemActionsMain"
    version = "1.0.0-${gitHash()} from ${gitBranch()} LTS (${getBuildDate()})"
    author = "gabriaum"
}