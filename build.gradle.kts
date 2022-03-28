val kotlinVersion = "1.6.10"

plugins {
    kotlin("multiplatform") version "1.6.10"
    kotlin("native.cocoapods") version "1.6.10"
    id("maven-publish")
}

group = "ru.mobiledev.lib.slf4k"
version = "0.1"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations {
            val logbackTest by compilations.creating {
                defaultSourceSet {
                    dependencies {
                        implementation("ch.qos.logback:logback-classic:1.2.10")
                        implementation(kotlin("test"))
                        implementation(kotlin("test-junit"))
                    }
                }

                // Create a test task to run the tests produced by this compilation:
                tasks.register<Test>("jvmLogbackTest") {
                    description = "Runs the integration tests with JVM Logback."
                    group = "verification"

                    useJUnit()

                    outputs.upToDateWhen { false }
                    mustRunAfter("test")

                    // Run the tests with the classpath containing the compile dependencies (including 'main'),
                    // runtime dependencies, and the outputs of this compilation:
                    classpath = compileDependencyFiles + runtimeDependencyFiles + output.allOutputs

                    // Run only the tests from this compilation's outputs:
                    testClassesDirs = output.classesDirs
                }
            }
        }
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
        compilations.all {
            kotlinOptions {
                sourceMap = true
                moduleKind = "umd"
                metaInfo = true
            }
        }
        browser()
        nodejs()
    }
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    /*configure(listOf(iosX64(), iosArm64())) {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }*/

    cocoapods {

        ios.deploymentTarget = "13.5"

        summary = "CocoaPods test library"
        homepage = "https://github.com/JetBrains/kotlin"

        /*pod("AFNetworking") {
            version = "~> 4.0.1"
        }*/

        /*pod("Logging") {
            path("/Users/gda/Projects/md/swift-log/")
            version = "~> 1.4.0"
        }*/

        framework {
            // Mandatory properties
            // Configure fields required by CocoaPods.
            summary = "Some description for a Kotlin/Native module"
            homepage = "Link to a Kotlin/Native module homepage"
            // Framework name configuration. Use this property instead of deprecated 'frameworkName'
            baseName = "slf4k"

            // Optional properties
            // (Optional) Dynamic framework support
            // isStatic = false
            // (Optional) Dependency export
//            export(project(":anotherKMMModule"))
            transitiveExport = true
            // (Optional) Bitcode embedding
            embedBitcode(org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.BITCODE)
        }

        // Maps custom Xcode configuration to NativeBuildType
        // xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        // xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val iosArm64Main by getting
        val iosX64Main by getting {
            dependsOn(iosArm64Main)
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.slf4j:slf4j-api:1.7.36")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.slf4j:slf4j-nop:1.7.36")
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
            }
        }
        val nativeTest by getting
        val iosMain by getting
        val iosTest by getting

        // https://github.com/ktorio/ktor/blob/main/ktor-client/ktor-client-curl/build.gradle.kts
        // https://github.com/benasher44/uuid
    }
}

task("processClasses") {
    this.dependsOn(tasks.findByName("jvmMainClasses"))
    setOf("jvmJar", "jvmTest", "jvmLogbackTest").forEach { tasks.findByName(it)?.dependsOn(this) }
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

// https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
