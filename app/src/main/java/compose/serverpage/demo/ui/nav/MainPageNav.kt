package compose.serverpage.demo.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import compose.serverpage.demo.ui.page.servers.ServersPage

enum class MainPageDestination(
    val iconDefault: ImageVector,
    val iconSelected: ImageVector,
    val route: String,
    val label: String,
) {
    Home(
        Icons.Default.Home,
        Icons.Outlined.Home,
        "main_page_home",
        "Information"
    ),

    Server(
        Icons.Default.Dns,
        Icons.Outlined.Dns,
        "main_page_server",
        "Server"
    ),
    Setting(
        Icons.Default.Settings,
        Icons.Outlined.Settings,
        "main_page_setting",
        "Setting"
    );

    fun getIcon(selected: Boolean) : ImageVector = if (selected) iconSelected else iconDefault
}

val LocalMainPageNav = staticCompositionLocalOf<NavHostController> {
    error("LocalMainPageNav Not Provide")
}

@Composable
fun MainPageNav(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalMainPageNav provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = MainPageDestination.Server.route,
            modifier = modifier
        ) {
            composable(route = MainPageDestination.Home.route) {
                Text("Information")
            }
            composable(route = MainPageDestination.Server.route) {
                ServersPage(
                    innerPadding = innerPadding
                )
            }
            composable(route = MainPageDestination.Setting.route) {
                Text("Setting")
            }
        }
    }
}