plugins {
    kotlin("jvm") version "1.6.21"
    `maven-publish`

    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "com.deltaclient"
version = "1.2.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.mojang:authlib:1.5.21")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
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

            val deltaNxUser = System.getenv("DELTA_NX_USER")
            val deltaNxPass = System.getenv("DELTA_NX_PASS")

            username.set(deltaNxUser)
            password.set(deltaNxPass)
        }
    }
}