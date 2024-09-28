import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.hivemq.extension")
}

group = "com.linole.hivemq"
description = "HiveMQ authentication extension"
version = "1.0.0"

hivemqExtension {
    name.set("Authentication extension")
    author.set("Ole Klokkhammer")
    priority.set(1000)
    startPriority.set(1000)
    mainClass.set("$group.extension.authentication.Main")
    sdkVersion.set("$version")
}

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/ole-klokkhammer/linole")
        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("com.hivemq:hivemq-extension-sdk:_")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}