tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":core:core-enum"))
    api(project(":domain"))
    implementation(project(":support:logging"))
    implementation(project(":storage:core-db"))
    testImplementation(project(":tests:api-docs"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation(kotlin("test"))
}