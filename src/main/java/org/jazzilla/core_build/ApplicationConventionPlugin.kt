package org.jazzilla.core_build

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jazzilla.core_build.utils.apply
import org.jazzilla.core_build.utils.configureCompose
import org.jazzilla.core_build.utils.configureAndroid

class ApplicationConventionPlugin : Plugin<Project> {
    
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application", "org.jetbrains.kotlin.android")
            }
        
            extensions.configure<BaseAppModuleExtension> {
                configureAndroid(this)
                configureCompose(this)
                defaultConfig.targetSdk = 33
            }
        }
    }
}