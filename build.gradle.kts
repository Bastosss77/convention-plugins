plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.spotless.gradle)
    compileOnly(libs.jacoco.gradle)
    compileOnly(libs.detekt.gradle)
}

gradlePlugin {
    plugins {
        register("kotlinLibrary") {
            id = "org.jazzilla.plugins.kotlinLibraryConventionPlugin"
            implementationClass = "org.jazzilla.core_build.KotlinLibraryConventionPlugin"
        }
    
        register("androidLibrary") {
            id = "org.jazzilla.plugins.androidLibraryConventionPlugin"
            implementationClass = "org.jazzilla.core_build.AndroidLibraryConventionPlugin"
        }
    
        register("composeLibrary") {
            id = "org.jazzilla.plugins.composeLibraryConventionPlugin"
            implementationClass = "org.jazzilla.core_build.ComposeLibraryConventionPlugin"
        }
    
        register("application") {
            id = "org.jazzilla.plugins.applicationConventionPlugin"
            implementationClass = "org.jazzilla.core_build.ApplicationConventionPlugin"
        }
        
        register("spotless") {
            id = "org.jazzilla.plugins.spotlessConventionPlugin"
            implementationClass = "org.jazzilla.core_build.SpotlessConventionPlugin"
        }
    
        register("jacoco") {
            id = "org.jazzilla.plugins.jacocoConventionPlugin"
            implementationClass = "org.jazzilla.core_build.JacocoConventionPlugin"
        }
    
        register("jacocoMerge") {
            id = "org.jazzilla.plugins.jacocoApplicationConventionPlugin"
            implementationClass = "org.jazzilla.core_build.JacocoApplicationConventionPlugin"
        }
    
        register("detekt") {
            id = "org.jazzilla.plugins.detektConventionPlugin"
            implementationClass = "org.jazzilla.core_build.DetektConventionPlugin"
        }
    }
}