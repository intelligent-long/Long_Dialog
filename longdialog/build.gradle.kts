plugins {
    id("com.android.library")
    `maven-publish`
}

group = "com.longx.intelligent.android.lib.longdialog"
version = "1.0"
val manualBuildTime = "2026 年 9 月 1 日"

val generatedSourcesDir = layout.projectDirectory.dir("src/main/java")

val generateBuildInfoTask = tasks.register("generateBuildInfo") {
    inputs.property("version", project.version.toString())
    inputs.property("buildTime", manualBuildTime)
    outputs.dir(generatedSourcesDir)
    doLast {
        val outDir = generatedSourcesDir.asFile
        val packageDir = File(outDir, "com/longx/intelligent/android/lib/longdialog/_build")
        if (!packageDir.exists()) {
            packageDir.mkdirs()
        }
        File(packageDir, "BuildInfo.java").writeText("""
            package com.longx.intelligent.android.lib.longdialog._build;

            public class BuildInfo {
                public static final String VERSION = "${project.version}";
                public static final String BUILD_TIME = "$manualBuildTime";
            }
        """.trimIndent())
    }
}

tasks.named("preBuild") {
    dependsOn(generateBuildInfoTask)
}

android {
    namespace = "com.longx.intelligent.android.lib.longdialog"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.longx.intelligent.android.lib.longdialog"
                artifactId = "long-dialog"
                version = "1.0"
            }
        }
    }
}