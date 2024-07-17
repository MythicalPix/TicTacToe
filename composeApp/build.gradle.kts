import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val majorVersion = 1
val minorVersion = 0
val patchVersion = 0
val verCode = majorVersion * 10000 + minorVersion * 100 + patchVersion
val verName = "$majorVersion.$minorVersion.$patchVersion"

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.room)
}

kotlin {
  androidTarget {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
  }

  jvm("desktop")

  sourceSets {
    val desktopMain by getting

    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.androidx.appcompat)

      implementation(libs.androidx.core.splashscreen)
      implementation(libs.koin.android)
      implementation(libs.koin.androidx.compose)
    }

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)

      api(libs.datastore.preferences)
      api(libs.datastore)
      api(libs.koin.core)

      implementation(libs.navigation.compose)
      implementation(libs.kotlinx.serialization.json)
      implementation(libs.material3.window.size)
      implementation(libs.room.runtime)
      implementation(libs.sqlite.bundled)
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)
      implementation(libs.lifecycle.viewmodel)
    }

    desktopMain.dependencies { implementation(compose.desktop.currentOs) }
  }
}

android {
  namespace = "com.kleinreveche.tictactoe"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  sourceSets["main"].res.srcDirs("src/androidMain/res")
  sourceSets["main"].resources.srcDirs("src/commonMain/resources")

  defaultConfig {
    applicationId = "com.kleinreveche.tictactoe"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = verCode
    versionName = verName
  }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
  buildTypes { getByName("release") { isMinifyEnabled = false } }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures { compose = true }
  dependencies { debugImplementation(compose.uiTooling) }
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "com.kleinreveche.tictactoe"
      packageVersion = verName

      windows {
        iconFile.set(project.file("src/commonMain/composeResources/drawable/icon_windows.ico"))
      }

      linux {
        iconFile.set(project.file("src/commonMain/composeResources/drawable/icon.png"))
      }
    }
  }
}

room { schemaDirectory("$projectDir/schemas") }

dependencies { ksp(libs.room.compiler) }
