package org.jazzilla.core_build

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jazzilla.core_build.utils.configureCompose

class ComposeLibraryConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()
            configureCompose(extension)
        }
    }
}