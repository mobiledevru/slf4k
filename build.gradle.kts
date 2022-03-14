val kotlinVersion = "1.6.10"

plugins {
    kotlin("multiplatform") version "1.6.10"
    id("maven-publish")
}

group = "ru.mobiledev.lib.slf4k"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
//            useJUnitPlatform()
            useJUnit()
        }
    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    ios {
        binaries {
            framework {
                baseName = "app-shared"
            }
        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
//    val nativeTarget = when {
//        hostOs == "Mac OS X" -> macosX64("native")
//        hostOs == "Linux" -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }

    
    sourceSets {
        val commonMain by getting {
            dependencies {
//                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:${kotlinVersion}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
//                implementation(kotlin("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}"))
//                implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                /*implementation("ch.qos.logback:logback-classic:1.2.10") {
                    exclude("org.slf4j", "slf4j-api")
                }*/
                implementation("org.slf4j:slf4j-nop:1.7.36")
                implementation("org.slf4j:slf4j-api:1.7.36")
                implementation("junit:junit:4.13")
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))

            }
        }
        /*val jvmTest by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:1.2.10") {
                    exclude("org.slf4j", "slf4j-api")
                }
                implementation("junit:junit:4.13")
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))

            }
        }*/
        /*val jsMain by getting {
            dependencies {
//                implementation(kotlin("org.jetbrains.kotlin:kotlin-stdlib-js:${kotlinVersion}"))
                implementation("org.jetbrains.kotlin:kotlin-stdlib-js:${kotlinVersion}")
            }
        }
        val jsTest by getting*/
//        val nativeMain by getting
//        val nativeTest by getting
        val iosMain by getting
        /*val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.6.3")
            }
        }*/
        val iosTest by getting
    }
}

/*tasks {
    val compileJava = named("compileJava", JavaCompile::class).get()
    val compileKotlin = named("compileKotlin", KotlinCompile::class).get()
    val compileGroovy = named("compileGroovy", GroovyCompile::class).get()
    val classes by getting

    compileGroovy.dependsOn.remove("compileJava")
    compileKotlin.setDependsOn(mutableListOf(compileGroovy))
    compileKotlin.classpath += files(compileGroovy.destinationDir)
    classes.setDependsOn(mutableListOf(compileKotlin))
}*/

task("processClasses") {
//    tasks.findByName("classes")?.dependsOn(this)
    tasks.findByName("jvmJar")?.dependsOn(this)
    doLast {
        ant.withGroovyBuilder {
            "delete"("includeemptydirs" to "true") {
                // by slf4j design
                "fileset"("dir" to "$buildDir/classes/java/main/org/slf4j/impl") {
                    "include"("name" to "**/*.class")
                }
                // exclude all slf4j classes to be completely replaced with original slf4j api implementation under jvm
                "fileset"("dir" to "$buildDir/classes/java/main/org/slf4j/") {
                    "include"("name" to "**/*.class")
                }
                "fileset"("dir" to "$buildDir/classes/kotlin/jvm/main/org/slf4j/") {
                    "include"("name" to "**/*.class")
                }
            }
        }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)
