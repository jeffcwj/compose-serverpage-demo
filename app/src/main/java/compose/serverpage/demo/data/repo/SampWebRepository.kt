package compose.serverpage.demo.data.repo

import compose.serverpage.demo.data.net.api.SampWebApi
import compose.serverpage.demo.data.repo.BaseRepository
import javax.inject.Inject

class SampWebRepository @Inject constructor(
    private val sampWebApi: SampWebApi
): BaseRepository() {

    companion object {
        private const val TAG = "SampWebRepository"
    }

    suspend fun getServerInfo(
        host: String,
        port: String
    ) = safeApiCall {
        sampWebApi.getServerInfo(host, port)
    }

    suspend fun getServers(
    ) = safeApiCall {
        sampWebApi.getServers()
    }

}