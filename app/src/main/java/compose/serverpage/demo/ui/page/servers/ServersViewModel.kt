package compose.serverpage.demo.ui.page.servers

import android.app.Application
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import compose.serverpage.demo.data.model.ServerInfoModel
import compose.serverpage.demo.data.model.ServerListModel
import compose.serverpage.demo.data.model.ServerStatsModel
import compose.serverpage.demo.data.model.enums.ServerPageEnum
import compose.serverpage.demo.data.net.onFailure
import compose.serverpage.demo.data.net.onSuccess
import compose.serverpage.demo.data.repo.SampWebRepository
import compose.serverpage.demo.utils.Toast
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ServersViewModel @Inject constructor(
    private val app: Application,
    private val repository: SampWebRepository
) : ViewModel() {

    companion object {
        private const val TAG = "ServersViewModel"
    }

    val serverList = mutableStateListOf<ServerInfoModel>()
    val isRefreshing = mutableStateOf(false)
    val currentPage = mutableStateOf(ServerPageEnum.Favorite)

    val serverStats by derivedStateOf {
        listOf(
            ServerStatsModel(Icons.Rounded.Dns, serverList.size, "Total Servers", Color(0xFF2196F3)),
            ServerStatsModel(Icons.Rounded.Group, serverList.sumOf { it.players }, "Online Players", Color(0xFF4CAF50)),
            ServerStatsModel(Icons.Rounded.Wifi, serverList.count { it.status == "online" }, "Online Servers", Color(0xFF9C27B0)),
            ServerStatsModel(Icons.Rounded.FavoriteBorder, serverList.count { it.isFavorite }, "Favorites", Color(0xFFF44336))
        )
    }

    init {
        getServers()
    }

    fun readLocalServerList(): ServerListModel {
        val servers = runCatching {
            File(app.getExternalFilesDir(null), "servers.json").readText()
        }.onFailure {
            Log.e(TAG, "readLocalServerList: ", it)
        }.getOrElse { "" }
        return runCatching {
            Gson().fromJson(servers, ServerListModel::class.java) ?: ServerListModel(emptyList())
        }.getOrElse {
            Log.e(TAG, "readLocalServerList: failed to parse JSON", it)
            ServerListModel(emptyList())
        }
    }

    fun writeLocalServerList(str: String) {
        runCatching {
            File(app.getExternalFilesDir(null), "servers.json").apply {
                parentFile?.mkdirs()
                writeText(str)
            }
        }
    }

    fun getServers() {
        fun addToList(data: ServerListModel) {
            serverList.clear()
            data.servers?.forEach {
                serverList.add(
                    ServerInfoModel(
                    host = it.host,
                    port = it.port
                )
                )
            }
            getServerInfos(data)
        }
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (currentPage.value == ServerPageEnum.Favorite) {
                val data = readLocalServerList()
                addToList(data)
                delay(500)
                isRefreshing.value = false
            } else if (currentPage.value == ServerPageEnum.Internet) {
                repository.getServers()
                    .onSuccess { data ->
                        isRefreshing.value = false
                        addToList(data)
                    }.onFailure { _,_,_ ->
                        isRefreshing.value = false
                        app.Toast("Failed to get Online Servers")
                    }
            }
        }
    }

    private fun getServerInfos(data: ServerListModel) {
        viewModelScope.launch(Dispatchers.IO) {
            data.servers?.forEachIndexed { index, item ->
                launch(Dispatchers.IO) {
                    repository.getServerInfo(item.host, item.port)
                        .onSuccess { data ->
                            runCatching {
                                serverList.set(index, data)
                            }
                        }
                }
            }
        }
    }

}