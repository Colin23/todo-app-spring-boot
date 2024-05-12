import de.thetaphi.forbiddenapis.gradle.CheckForbiddenApis

plugins {
	`java-library` // Apply the java-library plugin for API and implementation separation.
	id("io.freefair.lombok") version "8.3"
	id("de.thetaphi.forbiddenapis") version "3.6"
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	checkstyle
	idea
}

group = "com.colinmoerbe"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}

repositories {
	mavenCentral() // Use Maven Central for resolving dependencies.
}

dependencies {
	// Use JUnit Jupiter for testing.
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// ArchitectureTests dependencies.
	testImplementation("com.tngtech.archunit:archunit:1.2.0"){
		exclude(group = "org.slf4j") // Necessary to ignore the built-in slf4j-api:2.0.9 dependency
	}

	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Generic imported ForbiddenApis and custom self-written ones.
tasks.named<CheckForbiddenApis>("forbiddenApisMain").configure {
	bundledSignatures = setOf("jdk-unsafe", "jdk-deprecated", "jdk-internal", "jdk-non-portable", "jdk-system-out", "jdk-reflection")
	signaturesFiles = project.files("forbidden-apis.txt")
}

// Forbidden API tests for the 'test' directory
tasks.named<CheckForbiddenApis>("forbiddenApisTest").configure {
	bundledSignatures = setOf("jdk-unsafe", "jdk-deprecated", "jdk-internal", "jdk-non-portable", "jdk-reflection")
	signaturesFiles = project.files("forbidden-apis.txt")
	isEnabled = true
}

// Ignore the test directory
tasks.named<DefaultTask>("checkstyleTest").configure {
	isEnabled = false
}

tasks.named("check").configure {
	dependsOn(tasks.named("forbiddenApisMain"))
}

checkstyle {
	configFile = project.file("checkstyle.xml")
	toolVersion = "10.12.4"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<Jar> {
	manifest {
		attributes["Implementation-Version"] = version
	}
}
