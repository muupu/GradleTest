println("[sublib][build.gradle.kts]This is executed during the configuration phase.")
println(name)
println(project.name)
println(project.path)
println(project.projectDir)
println(project.buildDir)
println(project.version)
println()

extra["hasTests"] = true

val subVersion by extra("1.2.3")

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"(group = "commons-codec", name = "commons-codec", version = "1.2")
    }
}

tasks.register("encode") {
    doLast {
        val encodedString = org.apache.commons.codec.binary.Base64().encode("hello world\n".toByteArray())
        println(String(encodedString))
    }
}

tasks.register("printProperties") {
    doLast {
        println(subVersion)
    }
}

tasks.register<Copy>("copyReportsForArchiving") {
    from(layout.buildDirectory.file("reports/my-report.pdf"), layout.projectDirectory.file("src/docs/manual.pdf"))
    into(layout.buildDirectory.dir("toArchive"))
}