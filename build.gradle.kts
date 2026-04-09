plugins {
    id("java")
}

group = "com.luminia.mobplugin"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven("https://repo.luminiadev.com/snapshots")
    maven("https://repo.luminiadev.com/releases")
}

dependencies {
    compileOnly("com.koshakmine:Lumi:1.5.0-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.withType<ProcessResources> {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.withType<Jar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
}