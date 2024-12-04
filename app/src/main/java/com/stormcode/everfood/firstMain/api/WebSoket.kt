import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class WebSocketManager(
    private val url: String,
    private val user1: String,
    private val user2: String,
    private val senderType: String,
    private val recipientType: String,
    private val callback: WebSocketCallback
) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect() {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                val registerMessage = JSONObject().apply {
                    put("type", "register")
                    put("payload", JSONObject().apply {
                        put("userId", user1)
                        put("userType", senderType)
                    })
                }
                sendMessage(registerMessage.toString())
                callback.onConnected()
            }


            override fun onMessage(webSocket: WebSocket, text: String) {
                val message = JSONObject(text)
                when (message.getString("type")) {
                    "message" -> callback.onMessageReceived(message.getJSONObject("payload"))
                    "users" -> callback.onUsersUpdated(message.getJSONArray("payload"))
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                callback.onError(t.message ?: "Unknown error")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                callback.onDisconnected()
            }
        })
    }
    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Closing connection")
    }
}

interface WebSocketCallback {
    fun onConnected()
    fun onMessageReceived(message: JSONObject)
    fun onUsersUpdated(users: JSONArray)
    fun onError(error: String)
    fun onDisconnected()
}
