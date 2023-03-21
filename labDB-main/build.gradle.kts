plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.29")
}

application {
    mainClass.set("lab.App")
}

