import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.hivemq.extension")
    kotlin("jvm") version "1.6.20"
}

group = "com.linole.hivemq"
description = "HiveMQ jwt extension"
version = "1.0.0"

hivemqExtension {
    name.set("JWT extension")
    author.set("Ole Klokkhammer")
    priority.set(1000)
    startPriority.set(1000)
    mainClass.set("$group.extension.authentication.Main")
    sdkVersion.set("$version")
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
    implementation("com.hivemq:hivemq-extension-sdk:4.6.3")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}