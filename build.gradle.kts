plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish`
    // As suggested by https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
    // see https://vanniktech.github.io/gradle-maven-publish-plugin/
    id("com.vanniktech.maven.publish") version "0.31.0"
}

// CAUTION Until 2.0.1 (GitHub packages), the "group" was set to "ch.admin.eid".
//         For the sake of Maven Central publishing, it must now matches the Maven Central namespace
group = "io.github.swiyu-admin-ch"
version = "2.0.1"

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

/*
publishing {
    publications {
        register<MavenPublication>("gpr") {
            groupId = "ch.admin.eid"
            artifactId = "didresolver"
            version = "2.0.1"
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/swiyu-admin-ch/didresolver-kotlin")
            credentials {
                // For the GitHub packages, create a personal access token having 'write:packages' scope
                // and then store it into gradle.properties:
                // gpr.user=<your_GitHub_user>
                // gpr.token=****************************************
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
        maven {
            name = "BitNexusMavenHostedRepo"
            url = uri("https://nexus.bit.admin.ch/repository/bj-swiyu-maven-hosted")
            credentials {
                // For the BIT Nexus Maven repo, login onto https://nexus.bit.admin.ch with your u808***** account.
                // Ensure your U-account already have writing grants for the repo (bj-swiyu-maven-hosted).
                // Create an access user token (visit https://nexus.bit.admin.ch/#user/usertoken)
                // and then store it into gradle.properties:
                // nexus.user=********
                // nexus.token=********************************************
                username = project.findProperty("nexus.user") as String? ?: System.getenv("NEXUS_USERNAME")
                password = project.findProperty("nexus.token") as String? ?: System.getenv("NEXUS_TOKEN")
            }
        }
    }
}
 */

// As suggested by https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
mavenPublishing {
    // when publishing to https://central.sonatype.com/
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL, automaticRelease = false)

    signAllPublications()

    coordinates(group.toString(), "didresolver", version.toString())

    pom {
        name = "DID Resolver"
        description = "Language bindings for the swiyu DID resolver library in Kotlin"
        url = "https://github.com/swiyu-admin-ch/didresolver"
        licenses {
            license {
                name = "MIT License"
                url = "http://www.opensource.org/licenses/mit"
            }
        }
        developers {
            developer {
                id = "vst-bit"
                name = "vst-bit (Swiyu Omni Developer)"
                organization = "Swiyu"
                organizationUrl = "https://github.com/swiyu-admin-ch"
            }
        }
        scm {
            url = "https://github.com/swiyu-admin-ch/didresolver-kotlin/tree/main"
            connection = "scm:git:git://github.com/swiyu-admin-ch/didresolver-kotlin.git"
            developerConnection = "scm:git:ssh://github.com:swiyu-admin-ch/didresolver-kotlin.git"
        }
    }
}