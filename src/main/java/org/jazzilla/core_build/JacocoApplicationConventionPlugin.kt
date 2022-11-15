package org.jazzilla.core_build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.kotlin.dsl.task

//FIXME("Not fucking working, I don't know how to merge")

class JacocoApplicationConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            task(name = "jacocoFullReport", type = JacocoReport::class) {
                val executionDatas = mutableListOf<String>()
                val trees = mutableListOf<ConfigurableFileTree>()
                val coverageSource = mutableListOf<String>()
        
                emptyList<Project>().forEach {
                    dependsOn("${it.path}:testDebugUnitTestCoverage")
                    executionDatas.add("${it.buildDir}/outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
                    trees.add(fileTree("${it.buildDir}/tmp/kotlin-classes/debug/"))
                    coverageSource.add("${it.buildDir}/src/main/java")
                }
        
                group = "Reporting"
                description = "Merge coverage from submodules"
        
                executionData(executionDatas)
                val coverageSourceDirs = files(coverageSource)
        
                additionalClassDirs.from(files(trees))
                sourceDirectories.from(coverageSourceDirs)
                additionalSourceDirs.from(coverageSourceDirs)
        
                reports {
                    csv.required.set(true)
                    xml.required.set(false)
            
                    with(html) {
                        required.set(true)
                        outputLocation.set(file("$rootDir/build/reports/jacoco/coverage"))
                    }
                }
            }
        }
        
        
        /*with(target) {
            val theParent = parent
    
            checkNotNull(theParent)
    
            val projects = theParent.subprojects.filter {
                return@filter it.pluginManager.hasPlugin("jacoco")
            }
    
            println(theParent.allprojects.map { it.pluginManager.hasPlugin("jacoco") })
    
            println(projects.map {it.path})
            
            task(name = "jacocoFullReport", type = JacocoReport::class) {
                val executionDatas = mutableListOf<String>()
                val trees = mutableListOf<ConfigurableFileTree>()
                val coverageSource = mutableListOf<String>()
    
                emptyList<Project>().forEach {
                    dependsOn("${it.path}:testDebugUnitTestCoverage")
                    executionDatas.add("${it.buildDir}/outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
                    trees.add(fileTree("${it.buildDir}/tmp/kotlin-classes/debug/"))
                    coverageSource.add("${it.buildDir}/src/main/java")
                }
                
                group = "Reporting"
                description = "Merge coverage from submodules"
                
                executionData(executionDatas)
                
                val coverageSourceDirs = files(coverageSource)
                
                additionalClassDirs.from(files(trees))
                sourceDirectories.from(coverageSourceDirs)
                additionalSourceDirs.from(coverageSourceDirs)
                
                reports {
                    csv.required.set(true)
                    xml.required.set(false)
                    
                    with(html) {
                        required.set(true)
                        outputLocation.set(file("$rootDir/build/reports/jacoco/coverage"))
                    }
                }
            }
        }*/
    }
}