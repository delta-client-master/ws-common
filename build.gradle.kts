plugins {
    kotlin("jvm") version "1.6.10"
    `maven-publish`

    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "com.deltaclient"
version = "1.2"

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
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()

            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

nexusPublishing {
    repositories {
        create("deltaNexus") {
            nexusUrl.set(uri("https://nexus.deltaclient.com/repository/internal/"))
            snapshotRepositoryUrl.set(uri("https://nexus.deltaclient.com/repository/internal/"))

            useStaging.set(false)

            val deltaNxUser: String by project
            val deltaNxPass: String by project

            username.set(deltaNxUser)
            password.set(deltaNxPass)
        }
    }
}