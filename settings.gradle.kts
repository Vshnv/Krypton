rootProject.name = "krypton"

setOf("api", "server", "bootstrap").forEach {
    include(it)
    findProject(":$it")?.name = "${rootProject.name}-$it"
}
