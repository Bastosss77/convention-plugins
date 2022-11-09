package org.jazzilla.core_build

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jazzilla.core_build.utils.apply
import org.jazzilla.core_build.utils.configureAndroid

class AndroidLibraryConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(
                        "com.android.library",
                        "org.jetbrains.kotlin.android"
                )
            }
            
            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }
        }
    }
}