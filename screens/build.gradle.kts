import com.ivy.buildsrc.Hilt

apply<com.ivy.buildsrc.IvyPlugin>()

plugins {
    `android-library`
}

dependencies {
    Hilt()
    implementation(project(":common"))
    implementation(project(":core:data-model"))
}