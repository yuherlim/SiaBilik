plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // for firebase
    id("com.google.gms.google-services") version "4.4.1"

}



android {
    namespace = "com.example.siabilik"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.siabilik"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation ("com.google.android.material:material:1.13.0-alpha02")
    // for firebase
    implementation("com.google.firebase:firebase-firestore:24.11.0")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

<<<<<<< HEAD
    //
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.coil-kt:coil:2.4.0")
    implementation(libs.firebase.auth.ktx)

}
=======
}
>>>>>>> 2d9ede6072c0cd6ab97f5ccfd3ce96dc776f911a
