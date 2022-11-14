package org.jazzilla.core_build

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.task
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

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
                    excludes = listOf("jdk.internal.*")
                }
            }
            
            val extension = extensions.findByType<BaseAppModuleExtension>()
                    ?: extensions.findByType<LibraryExtension>()
                    ?: throw IllegalStateException("Not extension configurable for Jacoco")
            
            extension.buildTypes {
                getByName("debug") {
                    enableUnitTestCoverage = true
                    enableAndroidTestCoverage = false
                }
            }
            
            val buildTypes = extension.buildTypes.filter { it.enableUnitTestCoverage || it.enableAndroidTestCoverage }.map { it.name }
            val flavors = extension.productFlavors.map { it.name }.toMutableList()
            
            if(flavors.isEmpty()) flavors.add("")
            
            flavors.forEach { flavor ->
                buildTypes.forEach { build ->
                    val sourceName = if(flavor.isEmpty()) {
                        build
                    } else {
                        "$flavor$build".capitalized()
                    }
                    
                    val taskName = "test${sourceName.capitalized()}UnitTest"
                    println("Task -> $taskName")
    
                    task(name = "${taskName}Coverage", type = JacocoReport::class) {
                        dependsOn(taskName)
                        
                        group = "Reporting"
                        description = "Generate Jacoco coverage reports on the ${sourceName.capitalized()} build."
    
                        val kotlinTree = fileTree("$buildDir/tmp/kotlin-classes/$sourceName/")
                        val executionPath = "$buildDir/outputs/unit_test_code_coverage/${sourceName}UnitTest/$taskName.exec"
                        
                        executionData.from(executionPath)
    
                        val coverageSourceDirs = files(
                                "src/main/java",
                                "src/$flavor/java",
                                "src/$build/java"
                        )
                        
                        additionalClassDirs.from(kotlinTree)
                        sourceDirectories.from(coverageSourceDirs)
                        additionalSourceDirs.from(coverageSourceDirs)
                        
                        reports {
                            csv.required.set(true)
                            xml.required.set(true)
                            
                            with(html) {
                                required.set(true)
                            }
                        }
                    }
                }
            }
        }
    }
}