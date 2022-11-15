package org.jazzilla.core_build

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.task

class DetektConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")
            
            val projectSource = file(rootDir)
            //val configFile = files("$rootDir/config/detekt/detekt.yml")
            //val baselineFile = file("$rootDir/config/detekt/baseline.xml")
            val kotlinFiles = "**/*.kt"
            val resourceFiles = "**/resources/**"
            val buildFiles = "**/build/**"
            
            task(name = "detektAll", type = Detekt::class) {
                description = "Custom DETEKT build for all modules"
                parallel = true
                ignoreFailures = false
                autoCorrect = true
                buildUponDefaultConfig = true
                setSource(projectSource)
                //baseline.set(baselineFile)
                //config.setFrom(configFile)
                include(kotlinFiles)
                exclude(resourceFiles, buildFiles)
                reports {
                    html.required.set(true)
                    xml.required.set(false)
                    txt.required.set(false)
                }
            }
        }
    }
}
