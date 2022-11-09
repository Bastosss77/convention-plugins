package org.jazzilla.core_build.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal operator fun VersionCatalog.get(alias: String) : Provider<MinimalExternalModuleDependency> =
        findLibrary(alias).get()

internal fun PluginManager.apply(vararg pluginIds: String) {
    pluginIds.forEach { this.apply(it) }
}