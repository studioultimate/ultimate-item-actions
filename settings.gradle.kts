rootProject.name = "ultimate-item-actions"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs/libraries.toml"))
        }
    }
}