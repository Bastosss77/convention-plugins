package org.jazzilla.core_build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jazzilla.core_build.utils.apply

class KotlinLibraryConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(
                    "java-library",
                    "org.jetbrains.kotlin.jvm"
            )
    
            /*extensions.configure<BaseAppModuleExtension> {
                compileOptions.sourceCompatibility = JavaVersion.VERSION_11
                compileOptions.targetCompatibility = JavaVersion.VERSION_11
            }*/
        }
    }
}