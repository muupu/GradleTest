println("[sublib][build.gradle.kts]This is executed during the configuration phase.")

extra["hasTests"] = true


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