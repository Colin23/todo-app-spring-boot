import de.thetaphi.forbiddenapis.gradle.CheckForbiddenApis

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
val dependencySlf4jVersion = "2.0.13"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.slf4j:slf4j-api:$dependencySlf4jVersion")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

// Loads the versioning logic, and the custom Gradle tasks
apply(from = "versioning.gradle.kts")

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
