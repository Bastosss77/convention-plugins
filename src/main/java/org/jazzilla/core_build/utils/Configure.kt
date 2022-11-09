package org.jazzilla.core_build.utils

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 33
        
        defaultConfig {
            minSdk = 24
        }
        
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        
        kotlinOptions {
            jvmTarget = "11"
        }
        
        buildTypes {
            getByName("debug") {
                enableUnitTestCoverage = true
                enableAndroidTestCoverage = true
            }
        }
    }
    
    dependencies {
        add("implementation", libs["androidx-core"])
    }
}

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose-compiler").get().toString()
        }
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}