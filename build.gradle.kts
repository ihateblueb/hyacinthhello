plugins {
    kotlin("jvm") version "2.2.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://jitpack.io") {
        name = "jitpack"
    }

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        name = "extendedclip-repo"
    }
    maven("https://repo.aikar.co/content/groups/aikar/") {
        name = "aikar-repo"
    }
    maven("https://repo.rosewooddev.io/repository/public/") {
        name = "rosewood-repo"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:2.3.0")

    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("co.aikar:acf-bukkit:0.5.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.2")

    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("org.black_ixx:playerpoints:3.3.2")

    compileOnly("redis.clients:jedis:6.0.0")

    compileOnly("com.zaxxer:HikariCP:6.3.0")
    compileOnly("org.postgresql:postgresql:42.7.7")
    compileOnly("com.mysql:mysql-connector-j:9.3.0")

    compileOnly("org.jetbrains:annotations:23.0.0")
}

group = "site.remlit"
version = "2.5.0"
description = "HyacinthHello"

tasks {
    shadowJar {
        relocate("org.bstats", "site.remlit.hyacinthhello.bstats-bukkit")
    }
    runServer {
        minecraftVersion("1.21.10")
    }
}

val targetJavaVersion = 21

kotlin {
    jvmToolchain(targetJavaVersion)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

publishing {
    repositories {
        maven {
            name = "remlitsite"
            url = if (version.toString().contains("SNAPSHOT")) uri("https://repo.remlit.site/snapshots") else uri("https://repo.remlit.site/releases")

            credentials {
                username = System.getenv("REPO_ACTOR")
                password = System.getenv("REPO_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "site.remlit"
            artifactId = "hyacinthhello"
            version = project.version.toString()

            from(components["java"])

            pom {
                name = "hyacinthhello"
                url = "https://github.com/ihateblueb/hyacinthhello"

                developers {
                    developer {
                        id = "ihateblueb"
                        name = "ihateblueb"
                        email = "ihateblueb@proton.me"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/ihateblueb/hyacinthhello.git"
                    developerConnection = "scm:git:ssh://github.com/ihateblueb/hyacinthhello.git"
                    url = "https://github.com/ihateblueb/hyacinthhello"
                }
            }
        }
    }
}