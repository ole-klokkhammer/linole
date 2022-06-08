
plugins {
    java
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint")
    kotlin("jvm")
}

version = "1.1.4"
group = "com.linole"
val gitlabMavenAccessToken: String by project
val gitlabMavenLinoleUrl: String by project

repositories {
    jcenter()
    maven { url = uri(gitlabMavenLinoleUrl) }
}

dependencies {
    implementation(Kotlin.stdlib.jdk8)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:_")
    implementation("ch.qos.logback:logback-classic:_")
    api("org.eclipse.paho:org.eclipse.paho.client.mqttv3:_")
    api("org.eclipse.paho:org.eclipse.paho.mqttv5.client:_")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact("build/libs/lib-$version.jar")
            artifactId = "mqtt-client"
            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies")
                // Iterate over the api dependencies (we don't want the test ones), adding a <dependency> node for each
                configurations.api.get().allDependencies.forEach {
                    println("Appending dependency $it")
                    val dependencyNode = dependenciesNode.appendNode("dependency")
                    dependencyNode.appendNode("groupId", it.group)
                    dependencyNode.appendNode("artifactId", it.name)
                    dependencyNode.appendNode("version", it.version)
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitLab"
            url = uri(gitlabMavenLinoleUrl)
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value = gitlabMavenAccessToken
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
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
