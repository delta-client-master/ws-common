plugins {
    kotlin("jvm") version "1.6.10"
    `maven-publish`
}

group = "com.deltaclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.mojang:authlib:1.5.21")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            groupId = "com.deltaclient"
            artifactId = "ws-common"
            version = "1.0-SNAPSHOT"

            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}