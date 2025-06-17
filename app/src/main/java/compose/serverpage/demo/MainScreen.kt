package compose.serverpage.demo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import compose.serverpage.demo.ui.nav.MainPageDestination
import compose.serverpage.demo.ui.nav.MainPageNav
import compose.serverpage.demo.ui.widget.GlowingGradientBackground
import compose.serverpage.demo.utils.navigateSingleTopTo

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val bgColor = listOf(
        colorResource(R.color.tw_gray_900),
        Color.Black,
        colorResource(R.color.tw_indigo_900),
    )
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(bgColor)
        ),
    ) {
        GlowingGradientBackground()

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                val selectedTab by rememberSaveable { mutableStateOf(0) }
                val itemSize = 64.dp
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier,
                        colors = CardDefaults.cardColors().copy(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(0.5.dp, color = Color.White.copy(alpha = 0.3f))
                    ) {
                        Box {
                            Row( // background blur overlay
                                modifier = Modifier
                                    .matchParentSize()
                                    .height(itemSize + 12.dp)
                                    // we will do blur soon, but not now, now use background instead
                                    .background(colorResource(R.color.tw_gray_950).copy(alpha = 0.8f))
                            ) {}
                            Row( // glowing overlay
                                modifier = Modifier
                                    .matchParentSize()
                            ) {
                                val blurColor = listOf(
                                    colorResource(R.color.md2_purple_400),
                                    colorResource(R.color.md2_yellow_400),
                                    colorResource(R.color.md2_green_500),
                                )
                                MainPageDestination.entries.forEachIndexed { index, item ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(itemSize + 12.dp)
                                            .background(brush = Brush.radialGradient(
                                                colors = listOf(
                                                    blurColor[index].copy(alpha = 0.2f),
                                                    Color.Transparent
                                                ),
                                                center = Offset.Unspecified,
                                                radius = 140f
                                            ))
                                            .blur(20.dp)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .padding(12.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    MainPageDestination.entries.forEachIndexed { index, item ->
                                        Card(
                                            colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
                                            modifier = Modifier
                                                .size(itemSize),
                                            shape = RoundedCornerShape(20.dp),
                                            border = BorderStroke(0.5.dp, color = Color.White.copy(alpha = 0.3f))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clickable {
                                                        navController.navigateSingleTopTo(item.route)
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(item.getIcon(selectedTab == index), null)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        ) { innerPadding ->
            MainPageNav(
                modifier = Modifier,
                innerPadding = innerPadding,
                navController = navController
            )
        }
    }
}