plugins {
    kotlin("jvm") version "2.3.21"
    `maven-publish`
    // see https://gitlab.com/Plunts/plantuml-gradle-plugin
    id("io.gitlab.plunts.plantuml") version "2.4.1"
    // see https://kotlinlang.org/docs/dokka-javadoc.html#generate-javadoc-documentation
    id("org.jetbrains.dokka-javadoc") version "2.2.0"
    // As suggested by https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
    // see https://vanniktech.github.io/gradle-maven-publish-plugin/
    id("com.vanniktech.maven.publish") version "0.37.0"
}

// CAUTION Until 2.0.1 (GitHub packages), the "group" was set to "ch.admin.eid".
//         For the sake of Maven Central publishing, it must now match the Maven Central namespace
group = "ch.admin.swiyu"
version = "2.11.0-SNAPSHOT"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.4.10")
    implementation("net.java.dev.jna:jna:5.19.1")
    implementation("com.google.code.gson:gson:2.14.0")
}

kotlin {
    jvmToolchain(21)
}

// Just run: ./gradlew generateClassDiagrams
classDiagrams {
    diagram {
        // All properties are also available (and most likely more suited) in the defaults
        // For more information see official PlantUML documentation: https://plantuml.com/en/class-diagram
        style {
            hide("empty members")
            show("stereotype")
        }

        include(classes().withNameLike("ch.admin.eid.did_sidekicks.DidDoc*").insideOfProject())
        include(classes().withNameLike("ch.admin.eid.did_sidekicks.DidMethod*").insideOfProject())
        include(classes().withNameLike("ch.admin.eid.did_sidekicks.Jwk*").insideOfProject())
        include(classes().withNameLike("ch.admin.eid.did_sidekicks.VerificationMethod*").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.did_sidekicks.*Uniffi*").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.did_sidekicks.*Interface*").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.did_sidekicks.*Companion*").insideOfProject())
        include(classes().withNameLike("ch.admin.eid.didresolver.Did*").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.didresolver.DidResolveException$[A-Z]*").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.didresolver.*Kt").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.didresolver.*Uniffi*").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.didresolver.*Interface*").insideOfProject())
        exclude(classes().withNameLike("ch.admin.eid.didresolver.*Companion*").insideOfProject())
        exclude(interfaces())
        exclude(fields())
        writeTo(file("diagrams/didresolver.puml"))
        renderTo(file("diagrams/didresolver.svg"))
    }
}

// As suggested by https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
mavenPublishing {
    // when publishing to https://central.sonatype.com/
    publishToMavenCentral(automaticRelease = true)

    signAllPublications()

    coordinates(group.toString(), "didresolver", version.toString())

    pom {
        name = "DID Resolver"
        description = "Language bindings for the swiyu DID resolver library in Kotlin"
        url = "https://github.com/swiyu-admin-ch/didresolver-kotlin"
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
