import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.7.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.20"
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.7.20"
    kotlin("plugin.jpa") version "1.7.20"
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.7.20")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.7.20")
    }
}

apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
apply(plugin = "org.jetbrains.kotlin.plugin.noarg")

group = "com.moretech.vtb"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-security")
    // https://mvnrepository.com/artifact/com.playtika.reactivefeign/feign-reactor-spring-configuration
    implementation("com.playtika.reactivefeign:feign-reactor-spring-configuration:3.2.6")
    // https://mvnrepository.com/artifact/com.playtika.reactivefeign/feign-reactor-webclient
    implementation("com.playtika.reactivefeign:feign-reactor-webclient:3.2.6")
	// https://mvnrepository.com/artifact/com.playtika.reactivefeign/feign-reactor-webclient-core
    implementation("com.playtika.reactivefeign:feign-reactor-webclient-core:3.2.6")
	// https://mvnrepository.com/artifact/com.playtika.reactivefeign/feign-reactor-cloud
	implementation("com.playtika.reactivefeign:feign-reactor-cloud:3.2.6")
// https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging
    // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging
    implementation("io.github.microutils:kotlin-logging:3.0.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // https://mvnrepository.com/artifact/com.playtika.reactivefeign/feign-reactor-core
    implementation("com.playtika.reactivefeign:feign-reactor-core:3.2.6")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "18"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
