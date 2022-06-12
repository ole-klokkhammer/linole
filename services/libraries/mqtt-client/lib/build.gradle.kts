
plugins {
    java
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint")
    kotlin("jvm")
}

version = "1.1.13"
group = "com.linole"

repositories {
    jcenter()
    maven { url = uri("https://maven.pkg.github.com/${System.getenv("GITHUB_USERNAME")}/linole") }
}

dependencies {
    implementation(Kotlin.stdlib.jdk8)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:_")
    implementation("ch.qos.logback:logback-classic:_")
    api("org.eclipse.paho:org.eclipse.paho.client.mqttv3:_")
    api("org.eclipse.paho:org.eclipse.paho.mqttv5.client:_")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${System.getenv("GITHUB_USERNAME")}/linole")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            artifactId = "mqtt-client"
            from(components["kotlin"])
        }
    }
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
