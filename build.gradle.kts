plugins {
    java
}

group = "me.figgnus"
version = project.property("version").toString()

// 不用 toolchain：本机与 CI 的 JDK 可能不同，release 已锁定字节码版本
repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.spigot.api)
    compileOnly(libs.slimefun4)
    compileOnly(libs.jetbrains.annotations)
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release = 21
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}

defaultTasks("clean", "build")
