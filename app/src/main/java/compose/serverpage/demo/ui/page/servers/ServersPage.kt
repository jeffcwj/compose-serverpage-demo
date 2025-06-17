package compose.serverpage.demo.ui.page.servers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.NetworkCheck
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import compose.serverpage.demo.R
import compose.serverpage.demo.data.model.ServerInfoModel
import compose.serverpage.demo.data.model.enums.ServerPageEnum
import compose.serverpage.demo.ui.widget.SmallColorIconCardWithBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServersPage(
    viewModel: ServersViewModel = hiltViewModel(),
    innerPadding: PaddingValues
) {
    val titleColor = listOf(
        colorResource(R.color.md2_blue_400),
        colorResource(R.color.md2_purple_400),
        colorResource(R.color.md2_cyan_400),
    )
    val buttonBgColor = listOf(
        colorResource(R.color.tw_gray_900),
        colorResource(R.color.tw_blue_900),
        colorResource(R.color.tw_indigo_900),
    )

    Box(Modifier
        .fillMaxSize()
        /*.background(brush = Brush.linearGradient(bgColor))*/) {
        /*GlowingGradientBackground()*/
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = Color.Transparent
                    ),
                    title = {
                        Text(
                            text = "Servers",
                            style = MaterialTheme.typography.titleLarge.copy(
                                brush = Brush.horizontalGradient(
                                    colors = titleColor
                                ),
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                    },
                    actions = {
                        Button(
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = Color.Black.copy(alpha = 0.2f),
                                contentColor = colorResource(R.color.md2_blue_400).copy(alpha = 0.8f)
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            border = BorderStroke(width = 1.dp, color = colorResource(R.color.md2_blue_400).copy(alpha = 0.5f)),
                            modifier = Modifier,
                            onClick = {

                            },
                        ) {
                            Icon(Icons.Default.Add, null)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Add Server",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Spacer(Modifier.width(4.dp))
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                Modifier.padding(innerPadding)
            ) {

                ServerStatsPanel() // server stats

                Spacer(Modifier.height(8.dp))

                SearchPane() // Search

                // server list
                val isRefreshing by viewModel.isRefreshing
                PullToRefreshBox(
                    onRefresh = {
                        viewModel.getServers()
                    },
                    isRefreshing = isRefreshing
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 8.dp, bottom = (innerPadding.calculateTopPadding() + 16.dp), start = 8.dp, end = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            /*if (viewModel.serverList.isEmpty()) {
                                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                    Text("Server List is Empty", color = Color.White.copy(alpha = 0.7f)) // why this text is black in default
                                }
                            }*/
                        }
                        items(viewModel.serverList, key = { it.host + it.port} ) {
                            ServerItem(
                                item = it,
                                onItemClick = {

                                },
                                onFavoriteIconClick = {

                                }
                            )
                        }
                        item { // copyright
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Text("© Alyn SA-MP Mobile. All rights reserved.", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }

            }
        }

        FloatNavBar(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) // navigation
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatNavBar(
    modifier: Modifier = Modifier,
    viewModel: ServersViewModel = hiltViewModel()
) {
    var selected by remember { mutableStateOf(0) }
    var currentPage by viewModel.currentPage
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {

        }
        PrimaryTabRow(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(120.dp)
                .clip(CircleShape)
                .border(width = 1.dp, shape = CircleShape, color = colorResource(R.color.md2_blue_500).copy(alpha = 0.2f)),
            selectedTabIndex = selected,
            divider = {},
            indicator = {},
            containerColor = colorResource(R.color.tw_gray_950).copy(alpha = 0.9f)
        ) {
            Tab(
                selected = selected == 0,
                onClick = {
                    selected = 0
                    currentPage = ServerPageEnum.Favorite
                    viewModel.getServers()
                },
                text = {
                    Icon(Icons.Rounded.Book, null, tint = if (selected != 0) colorResource(R.color.md2_blue_500).copy(alpha = 0.5f) else colorResource(R.color.md2_blue_500))
                }
            )
            Tab(
                selected = selected == 1,
                onClick = {
                    selected = 1
                    currentPage = ServerPageEnum.Internet
                    viewModel.getServers()
                },
                text = {
                    Icon(Icons.Rounded.Public, null, tint = if (selected != 1) colorResource(R.color.md2_blue_500).copy(alpha = 0.5f) else colorResource(R.color.md2_blue_500))
                }
            )
        }
    }
}

@Composable
fun SearchPane(
    modifier: Modifier = Modifier,
    viewModel: ServersViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var searchValue by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchValue,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.3f),
                unfocusedIndicatorColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.2f),
                focusedLeadingIconColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.8f),
                unfocusedLeadingIconColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.8f),
                focusedPlaceholderColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.5f),
                unfocusedPlaceholderColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.2f),
                unfocusedLabelColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.8f),
            ),
            label = { Text("Search") },
            placeholder = { Text("input name, host, or gamemode") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(Icons.Rounded.Search, null)
            },
            // not planning to do filter&order xd, due to current request way
            /*trailingIcon = {
                Row {
                    IconButton (
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            contentColor = colorResource(R.color.md2_blue_500).copy(alpha = 0.8f)
                        ),
                        onClick = {

                        }
                    ) {
                        Icon(Icons.Rounded.Sort, null)
                    }
                }
            },*/
            singleLine = true,
            onValueChange = {
                searchValue = it
            },
        )

        /*Button(
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Black,
                contentColor = colorResource(R.color.md2_blue_500)
            ),
            border = BorderStroke(1.dp, colorResource(R.color.md2_blue_500).copy(alpha = 0.3f)),
            onClick = {

            }
        ) {
            Icon(Icons.Rounded.Sort, null)
        }*/
    }
}

@Composable
private fun ServerStatsPanel(
    modifier: Modifier = Modifier,
    viewModel: ServersViewModel = hiltViewModel()
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        viewModel.serverStats.forEach {
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = it.color.copy(alpha = 0.1f),
                    contentColor = it.color
                ),
                border = BorderStroke(width = 1.dp, it.color.copy(alpha = 0.2f)),
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    Modifier.padding(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = it.icon,
                        contentDescription = null
                    )
                    Spacer(Modifier.size(4.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = it.count.toString(),
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServerItem(
    modifier: Modifier = Modifier,
    item: ServerInfoModel,
    viewModel: ServersViewModel = hiltViewModel(),
    onFavoriteIconClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Black.copy(alpha = 0.2f),
            // contentColor = CardDefaults.cardColors().contentColor.copy(alpha = 0.8f)
        ),
        border = BorderStroke(width = 1.dp, Color.Gray.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.clickable {
                onItemClick()
            }.padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 12.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Text(text = "●", style = MaterialTheme.typography.titleLarge, color = if (item.status == "online") colorResource(R.color.md2_green_500) else colorResource(R.color.md2_red_500))
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        Modifier.animateContentSize()
                    ) {
                        Text(
                            modifier = Modifier,
                            text = if (item.status == "offline") "Server unavailable" else item.hostname,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }

                }
                IconButton(
                    onClick = onFavoriteIconClick
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = CardDefaults.cardColors().contentColor.copy(alpha = 0.8f)
                    )
                }
            }

            FlowRow(
                modifier = Modifier.padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SmallColorIconCardWithBorder(
                    Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    imageVector = Icons.Rounded.Dns,
                    color = colorResource(R.color.md2_blue_500),
                    iconSize = 14.dp,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "${item.host}:${item.port}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                AnimatedVisibility(item.gamemode.isNotBlank()) {
                    SmallColorIconCardWithBorder(
                        Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        imageVector = Icons.Rounded.SportsEsports,
                        color = colorResource(R.color.md2_purple_400),
                        iconSize = 14.dp,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = item.gamemode,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 4.dp),
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Rounded.Group,
                        contentDescription = null,
                    )
                    Text("Players", style = MaterialTheme.typography.labelMedium)
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = colorResource(R.color.md2_green_500))) {
                                        append("${item.players}")
                                    }
                                    append("/${item.maxplayers}")
                               },
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Rounded.NetworkCheck,
                        contentDescription = null,
                    )
                    Text("Ping", style = MaterialTheme.typography.labelMedium)
                    Spacer(Modifier.weight(1f))
                    val pingColor = if (item.ping < 50)
                                        colorResource(R.color.md2_green_500)
                                    else if (item.ping < 100)
                                        colorResource(R.color.md2_yellow_400)
                                    else colorResource(R.color.md2_red_500)
                    Text(
                        text = "${item.ping}ms",
                        style = MaterialTheme.typography.labelMedium,
                        color = pingColor
                    )
                }
            }
        }
    }
}

