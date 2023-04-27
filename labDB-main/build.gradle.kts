plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.29")
    implementation("com.toedter:jcalendar:1.4")
}

application {
    mainClass.set("lab.App")
}

