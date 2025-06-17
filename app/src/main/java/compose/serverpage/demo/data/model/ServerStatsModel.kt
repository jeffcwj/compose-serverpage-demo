package compose.serverpage.demo.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ServerStatsModel(
    val icon: ImageVector,
    val count: Int,
    val title: String,
    val color: Color
)
