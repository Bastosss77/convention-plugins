package org.jazzilla.core_build

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class SpotlessConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.diffplug.spotless")
            
            val spotless = extensions.getByType<SpotlessExtension>()
            
            spotless.apply {
                ratchetFrom("origin/develop")
                
                kotlin {
                    ktlint()
                            .userData(mapOf("android" to "true"))
                    trimTrailingWhitespace()
                    indentWithSpaces()
                    endWithNewline()
                }
            }
        }
    }
}