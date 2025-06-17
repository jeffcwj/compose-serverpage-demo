package compose.serverpage.demo.data.model

data class ServerInfoModel(
    val host: String = "",
    val port: String = "",
    val hostname: String = "Loading...",
    val gamemode: String = "",
    val language: String = "",
    val players: Int = 0,
    val maxplayers: Int = 0,
    val ping: Int = 0,
    val password: Boolean = false,
    val status: String = "online",
    val queryTime: Int = 0,
    val rules: ServerRuleModel? = null,

    val isFavorite: Boolean = false,
)



