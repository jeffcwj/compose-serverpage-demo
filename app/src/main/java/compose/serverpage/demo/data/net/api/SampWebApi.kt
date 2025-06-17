package compose.serverpage.demo.data.net.api

import retrofit2.http.GET
import retrofit2.http.Query
import compose.serverpage.demo.data.model.ServerInfoModel
import compose.serverpage.demo.data.model.ServerListModel

interface SampWebApi {


    @GET("/api/server-info")
    suspend fun getServerInfo(
        @Query("host") host: String,
        @Query("port") port: String,
    ) : ServerInfoModel

    @GET("/api/servers")
    suspend fun getServers() : ServerListModel

}