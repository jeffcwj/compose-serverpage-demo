package compose.serverpage.demo.data.model

data class ServerListModel(
    val servers: List<ServerEntry>?
) {
    data class ServerEntry(
        val host: String,
        val port: String
    )
}


