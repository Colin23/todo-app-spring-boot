import de.thetaphi.forbiddenapis.gradle.CheckForbiddenApis
import java.util.Properties

plugins {
	java
    idea
    checkstyle
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
	id("io.freefair.lombok") version "8.6"
	id("de.thetaphi.forbiddenapis") version "3.7"
}

group = "com.colinmoerbe"

springBoot {
    buildInfo()
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom ("org.springframework.boot:spring-boot-dependencies:3.3.0")
        mavenBom ("org.junit:junit-bom:5.10.2")
    }
}
val checkstyleVersion = "10.17.0"
val dependencyArchunitVersion = "1.3.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("com.tngtech.archunit:archunit:$dependencyArchunitVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

class ApplicationVersion(
    private val major: Int,
    private val minor: Int,
    private val patch: Int,
    private val release: Boolean
) {
    private fun getRelease(): String {
        return if (release) "" else "-SNAPSHOT"
    }

    fun getVersion(): String {
        return "$major.$minor.$patch" + getRelease()
    }
}

val loadVersion: () -> String = {
    val versionPropertiesFile = File("src/main/resources/version.properties")

    if (!versionPropertiesFile.exists()) {
        throw Exception("No version.properties file found")
    }

    val versionProperties = Properties()

    versionPropertiesFile.inputStream().use { stream ->
        versionProperties.load(stream)
    }

    val major = versionProperties.getProperty("major")?.toInt() ?: 0
    val minor = versionProperties.getProperty("minor")?.toInt() ?: 0
    val patch = versionProperties.getProperty("patch")?.toInt() ?: 0
    val release = versionProperties.getProperty("release")?.toBoolean() ?: false

    val version = ApplicationVersion(major, minor, patch, release)
    version.getVersion()
}

version = loadVersion()

tasks.withType<Jar> {
    manifest {
        attributes["Implementation-Version"] = version
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<CheckForbiddenApis>("forbiddenApisMain").configure {
	bundledSignatures = setOf("jdk-unsafe", "jdk-deprecated", "jdk-internal", "jdk-non-portable", "jdk-system-out", "jdk-reflection")
	signaturesFiles = project.files("forbidden-apis.txt")
}

tasks.named<CheckForbiddenApis>("forbiddenApisTest").configure {
	bundledSignatures = setOf("jdk-unsafe", "jdk-deprecated", "jdk-internal", "jdk-non-portable", "jdk-reflection")
	signaturesFiles = project.files("forbidden-apis.txt")
	isEnabled = true
}

tasks.named<DefaultTask>("checkstyleTest").configure {
	isEnabled = false
}

tasks.named("check").configure {
	dependsOn(tasks.named("forbiddenApisMain"))
}

tasks.register("majorVersionUpdate") {
    group = "versioning"
    description = "Bump to next major version"

    doFirst {
        val versionFile = File("src/main/resources/version.properties")
        val properties = Properties()

        versionFile.inputStream().use { stream ->
            properties.load(stream)
        }

        properties.setProperty("major", (properties.getProperty("major").toInt() + 1).toString())
        properties.setProperty("minor", "0")
        properties.setProperty("patch", "0")

        versionFile.outputStream().use { properties.store(it, null) }
    }
}

tasks.register("minorVersionUpdate") {
    group = "versioning"
    description = "Bump to next minor version"

    doFirst {
        val versionFile = file("src/main/resources/version.properties")
        val properties = Properties()
        versionFile.inputStream().use { properties.load(it) }

        properties.setProperty("minor", (properties.getProperty("minor").toInt() + 1).toString())
        properties.setProperty("patch", "0")

        versionFile.outputStream().use { properties.store(it, null) }
    }
}

tasks.register("patchVersionUpdate") {
    group = "versioning"
    description = "Bump to next patch version"

    doFirst {
        val versionFile = file("src/main/resources/version.properties")
        val properties = Properties()
        versionFile.inputStream().use { properties.load(it) }

        properties.setProperty("patch", (properties.getProperty("patch").toInt() + 1).toString())

        versionFile.outputStream().use { properties.store(it, null) }
    }
}

tasks.register("releaseVersion") {
    group = "versioning"
    description = "Make the version a release"

    doFirst {
        val versionFile = file("src/main/resources/version.properties")
        val properties = Properties()
        versionFile.inputStream().use { properties.load(it) }

        properties.setProperty("release", "true")

        versionFile.outputStream().use { properties.store(it, null) }
    }
}

tasks.register("preReleaseVersion") {
    group = "versioning"
    description = "Make the version a pre release"

    doFirst {
        val versionFile = file("src/main/resources/version.properties")
        val properties = Properties()
        versionFile.inputStream().use { properties.load(it) }

        properties.setProperty("release", "false")

        versionFile.outputStream().use { properties.store(it, null) }
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

checkstyle {
	configFile = project.file("checkstyle.xml")
	toolVersion = checkstyleVersion
}
