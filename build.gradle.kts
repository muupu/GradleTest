import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

println("[build.gradle.kts]This is executed during the configuration phase.")

allprojects {
    // Set a default value
    extra["hasTests"] = false

    afterEvaluate {
        if (extra["hasTests"] as Boolean) {
            println("Adding testsub task to $project")
            tasks.register("testsub") {
                doLast {
                    println("Running tests for $project")
                }
            }
        }
    }
}

gradle.afterProject {
    if (state.failure != null) {
        println("Evaluation of $project FAILED")
    } else {
        println("Evaluation of $project succeeded")
    }
}

gradle.taskGraph.beforeTask {
    println("executing $this ...")
}

gradle.taskGraph.afterTask {
    if (state.failure != null) {
        println("execution FAILED")
    } else {
        println("execution done")
    }
}

plugins {
    kotlin("jvm") version "1.6.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

tasks.register("hello"){
    doLast{
        println("Hello world...")
    }
}

tasks.create("greeting2") {
    doLast { println("Hello, World2!") }
}

task("greeting") {
    doLast { println("Hello, World!") }
}

tasks.register("intro") {
    dependsOn("hello")
    doLast {
        println("I'm Gradle")
    }
}

tasks.register("taskX") {
    dependsOn("taskY")
    doLast {
        println("taskX")
    }
}
tasks.register("taskY") {
    doLast {
        println("taskY")
    }
}

tasks.register("taskZ") {
    doLast {
        println("taskZ")
    }
}

tasks.named("taskZ") { dependsOn("taskX", "taskY")}

tasks.register("filter") {
    doLast {
        val strs = listOf("a", "bc", "abc", "abcd")
        fileList(strs).forEach { str -> println(str) }
    }
}

fun fileList(strs: List<String>): List<String> = strs.filter { str -> str.length >= 3 }

defaultTasks("myclean", "myrun")
tasks.register("myclean") {
    doLast {
        println("Default Cleaning!")
    }
}

tasks.register("myrun") {
    doLast {
        println("Default Running!")
    }
}

tasks.register("other") {
    doLast {
        println("I'm not a default task!")
    }
}

