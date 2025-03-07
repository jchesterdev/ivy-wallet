package com.ivy.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ivy.navigation.destinations.onboarding.OnboardingGraph

@Stable
data class OnboardingScreens(
    val loginOrOffline: @Composable () -> Unit,
    val importBackup: @Composable () -> Unit,
    val setCurrency: @Composable () -> Unit,
    val addAccounts: @Composable () -> Unit,
    val addCategories: @Composable () -> Unit,
)

fun NavGraphBuilder.onboardingGraph(
    screens: OnboardingScreens
) {
    navigation(
        route = OnboardingGraph.route,
        startDestination = OnboardingGraph.startDestination,
    ) {
        composable(OnboardingGraph.loginOrOffline.route) {
            screens.loginOrOffline()
        }
        composable(OnboardingGraph.importBackup.route) {
            screens.importBackup()
        }
        composable(OnboardingGraph.setCurrency.route) {
            screens.setCurrency()
        }
        composable(OnboardingGraph.addAccounts.route) {
            screens.addAccounts()
        }
        composable(OnboardingGraph.addCategories.route) {
            screens.addCategories()
        }
    }
}