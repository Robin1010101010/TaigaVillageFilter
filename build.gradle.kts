plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.seedfinding.com")
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.seedfinding:mc_math:1.171.0")
    implementation("com.seedfinding:mc_seed:1.171.1")
    implementation("com.seedfinding:mc_core:1.210.0")
    implementation("com.seedfinding:mc_noise:1.171.1")
    implementation("com.seedfinding:mc_biome:1.171.1")
    implementation("com.seedfinding:mc_terrain:1.171.1")
    implementation("com.seedfinding:mc_reversal:1.171.1")
    implementation("com.seedfinding:mc_feature:1.171.10")
    implementation("com.github.profotoce59:VillageGenerator:main-SNAPSHOT") { isTransitive = false }

    implementation(platform("org.apache.logging.log4j:log4j-bom:2.26.1"))
    implementation("org.apache.logging.log4j:log4j-api:\${log4j-api.version}")
    implementation("org.apache.logging.log4j:log4j-core:\${log4j-core.version}")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}