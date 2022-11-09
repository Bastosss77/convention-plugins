package org.jazzilla.core_build

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.jazzilla.core_build.utils.apply
import org.jetbrains.kotlin.gradle.utils.`is`
import java.util.Locale

/**
 * Jacoco plugin must be applied in last position to get library our application plugin
 */
class JacocoConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                
                apply("jacoco")
            }
            
            tasks.withType(Test::class.java) {
                with(this.extensions.getByType<JacocoTaskExtension>()) {
                    isIncludeNoLocationClasses = true
                    exclude(listOf("jdk.internal.*"))
                }
            }
            
            val extension = extensions.findByType<BaseAppModuleExtension>()
                    ?: extensions.findByType<LibraryExtension>()
                    ?: throw IllegalStateException("Not extension configurable for Jacoco")
    
            val buildTypes = extension.buildTypes.map { it.name }
            val flavors = extension.productFlavors.map { it.name }.toMutableList()
            
            if(flavors.isEmpty()) flavors.add("")
            
            flavors.forEach { flavor ->
                buildTypes.forEach { build ->
                    val (sourceName, sourcePath) = if(flavor.isEmpty()) {
                        build to build
                    } else {
                        "$flavor$build.capitalize()" to "$flavor/$build"
                    }
                    
                    val taskName = "test${sourceName.capitalized()}UnitTest"
                    println("Task -> $taskName")
                }
            }
        }
    }
}