import de.fayard.refreshVersions.core.extensions.gradle.passwordCredentials

plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "com.linole"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven {
        url = uri(System.getenv("GITLAB_MAVEN_LINOLE_URL"))
        credentials {
            username = System.getenv("GITLAB_MAVEN_LINOLE_USERNAME")
            password = System.getenv("GITLAB_MAVEN_LINOLE_PASSWORD")
        }
    }
}

dependencies {
    implementation(Kotlin.stdlib.jdk8)
    implementation(Ktor.server.core)
    implementation(Ktor.features.jackson)
    implementation(Koin.ktor)
    implementation(Ktor.server.netty)
    implementation("ch.qos.logback:logback-classic:_")
    implementation("com.linole:mqtt-client:_")
}

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "io.ktor.server.netty.EngineMain"))
        }
    }
}