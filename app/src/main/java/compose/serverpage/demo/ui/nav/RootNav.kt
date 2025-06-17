package compose.serverpage.demo.ui.nav

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import compose.serverpage.demo.MainScreen
import compose.serverpage.demo.ui.theme.MyTheme

enum class RootDesc(
    val route: String
) {
    Main("main_screen"),
}

val LocalRootNav = staticCompositionLocalOf<NavHostController> {
    error("LocalRootNav Not Provide")
}

@Composable
fun RootNav() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalRootNav provides navController,
    ) {
        MyTheme(darkTheme = true) {
            Surface(
                color = Color.Transparent,
            ) {
                RootNavHost(navController)
            }
        }
    }
}

@Composable
fun RootNavHost(
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = rootNavController,
        startDestination = RootDesc.Main.route,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }) + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        }
    ) {
        composable(route = RootDesc.Main.route) {
            MainScreen()
        }
    }
}