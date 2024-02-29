// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    kotlin("plugin.serialization") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.18" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.5" apply false
}
