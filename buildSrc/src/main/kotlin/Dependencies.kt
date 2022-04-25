object Dependencies {
    const val APP_COMPAT = "appCompat"
    val CORE = "core"
    val PAGING = "paging"
    val CONSTRAINT_LAYOUT = "constraintlayout"
    val SUPPORT = "support"
    val ACTIVITY_KTX = "activity-ktx"
    val FRAGMENT_KTX = "fragment-ktx"
    val androidXDependencies = mapOf(
        APP_COMPAT to Dependency("androidx.appcompat", "appcompat", "1.4.1"),
        CORE to Dependency("androidx.core", "core-ktx", "1.7.0"),
        PAGING to Dependency("androidx.paging", "paging-runtime-ktx", "3.1.1"),
        CONSTRAINT_LAYOUT to Dependency("androidx.constraintlayout", "constraintlayout", "2.1.3"),
        SUPPORT to Dependency("androidx.legacy", "legacy-support-v4", "1.0.0"),
        ACTIVITY_KTX to Dependency("androidx.activity", "activity-ktx", "1.4.0"),
        FRAGMENT_KTX to Dependency("androidx.fragment", "fragment-ktx", "1.4.1"),
    )
}