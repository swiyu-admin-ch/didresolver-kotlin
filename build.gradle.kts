plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish`
}

group = "uniffi"
version = "0.0.1"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
    implementation("net.java.dev.jna:jna:5.14.0")
    implementation("com.google.code.gson:gson:2.10.1")
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        register<MavenPublication>("gpr") {
            groupId = "uniffi"
            artifactId = "didresolver"
            version = "0.0.1"
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/admin-ch-ssi/didresolver-kotlin")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}