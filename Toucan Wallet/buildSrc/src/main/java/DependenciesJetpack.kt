object VersionsJetpack {

    const val androidx = "1.0.0"
    const val androidxCore = "1.1.0"
    const val androidxAppcompat = "1.0.2"
    const val androidxConstraintLayout = "2.0.0-alpha2"
    const val androidxLegacySupport = "1.0.0"
    const val androidxRoom = "2.2.2"
    const val androidxLifecycle = "2.0.0"
    const val androidxMaterial = "1.0.0"
    const val androidxNavigation = "2.1.0"

    const val archWorkManager = "1.0.0-alpha10"

    const val playServiceMaps = "15.0.1"
    const val playServices = "16.0.0"

    const val mapsUtils = "0.5+"

    const val multidex = "1.0.3"
}

object DependenciesJetpack {

    const val androidxCoreKtxLib = "androidx.core:core-ktx:${VersionsJetpack.androidxCore}"
    const val androidxAppcompatLib = "androidx.appcompat:appcompat:${VersionsJetpack.androidxAppcompat}"
    const val androidxConstraintLayoutLib = "androidx.constraintlayout:constraintlayout:${VersionsJetpack.androidxConstraintLayout}"
    const val androidxCardviewLib = "androidx.cardview:cardview:${VersionsJetpack.androidx}"
    const val androidxVectorDrawableLib = "androidx.vectordrawable:vectordrawable:${VersionsJetpack.androidx}"
    const val androidxLegacySupportLib = "androidx.legacy:legacy-support-v4:${VersionsJetpack.androidxLegacySupport}"
    const val androidxMaterialLib = "com.google.android.material:material:${VersionsJetpack.androidxMaterial}"

    const val androidxRoomLib = "androidx.room:room-runtime:${VersionsJetpack.androidxRoom}"
    const val androidxRoomCompiler = "androidx.room:room-compiler:${VersionsJetpack.androidxRoom}"

    const val androidxLifecycleExtensionsLib = "androidx.lifecycle:lifecycle-extensions:${VersionsJetpack.androidxLifecycle}"
    const val androidxLifecycleViewModelsLib = "androidx.lifecycle:lifecycle-viewmodel:${VersionsJetpack.androidxLifecycle}"
    const val androidxLifecycleRuntimeLib = "androidx.lifecycle:lifecycle-runtime:${VersionsJetpack.androidxLifecycle}"
    const val androidxLifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${VersionsJetpack.androidxLifecycle}"

    const val androidxNavigationFragmentLib = "androidx.navigation:navigation-fragment-ktx:${VersionsJetpack.androidxNavigation}"
    const val androidxNavigationUiLib = "androidx.navigation:navigation-ui-ktx:${VersionsJetpack.androidxNavigation}"

    const val archWorkManagerLib = "android.arch.work:work-runtime:${VersionsJetpack.archWorkManager}"

    const val playServiceMapsLib = "com.google.android.gms:play-services-maps:${VersionsJetpack.playServiceMaps}"
    const val playServicesLocation = "com.google.android.gms:play-services-location:${VersionsJetpack.playServices}"

    const val mapsUtilsLib = "com.google.maps.android:android-maps-utils:${VersionsJetpack.mapsUtils}"

    const val multidexLib = "com.android.support:multidex:${VersionsJetpack.multidex}"
}