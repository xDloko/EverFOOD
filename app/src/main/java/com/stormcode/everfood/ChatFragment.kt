package com.stormcode.everfood

import WebSocketCallback
import WebSocketManager
import android.util.Log
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.adapters.ChatAdapter
import com.stormcode.everfood.firstMain.api.RetrofitClient
import com.stormcode.everfood.firstMain.Message
import com.stormcode.everfood.firstMain.MessagesResponse
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Handler
import android.os.Looper

class ChatFragment : Fragment() {
    private lateinit var webSocketManager: WebSocketManager
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private var userType: String? = null
    private var storeType: String? = null
    private lateinit var sendButton: View
    private var storeId: String? = null
    private lateinit var chatAdapter: ChatAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 8000



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)
        messageInput = view.findViewById(R.id.messageInput)
        sendButton = view.findViewById(R.id.sendButton)
        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        val userId = context?.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)?.getString("userId", null)
        chatAdapter = ChatAdapter(mutableListOf(), userId!!)
        chatRecyclerView.adapter = chatAdapter

        storeId = arguments?.getString("tienda_id")
        storeType = "Tienda"
        userType = "User"



        if (storeId == null || userId == null) {
            Toast.makeText(requireContext(), "Error: Faltan identificadores", Toast.LENGTH_SHORT).show()
            return view
        }

        loadChatHistory(userId, userType!!, storeId!!, storeType!!)

        if (userId != null && storeId != null) {

            webSocketManager = WebSocketManager(
                url = "wss://api-server-production-12d3.up.railway.app/",
                user1 = userId,
                user2 = storeId!!,
                senderType = userType!!,
                recipientType = storeType!!,
                callback = object : WebSocketCallback {
                    override fun onConnected() {
                        activity?.runOnUiThread {
                            Toast.makeText(requireContext(), "Conectado al chat", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onMessageReceived(message: JSONObject) {
                        requireActivity().runOnUiThread {
                            chatAdapter.addMessage(message)
                            loadChatHistory(userId, userType!!, storeId!!, storeType!!)
                        }
                    }

                    override fun onUsersUpdated(users: JSONArray) {
                        Log.d("ChatFragment", "Usuarios actualizados: $users")
                    }

                    override fun onError(error: String) {
                        activity?.runOnUiThread {
                            Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onDisconnected() {
                        activity?.runOnUiThread {
                            Toast.makeText(requireContext(), "Desconectado del chat", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
            webSocketManager.connect()
        } else {
            Toast.makeText(requireContext(), "Error: Faltan identificadores", Toast.LENGTH_SHORT).show()
        }

        sendButton.setOnClickListener {
            val text = messageInput.text.toString()
            if (text.isNotEmpty()) {
                val message = JSONObject().apply {
                    put("type", "message")
                    put("payload", JSONObject().apply {
                        put("sender", userId)
                        put("senderType", userType)
                        put("recipient", storeId)
                        put("recipientType", storeType)
                        put("content", text)
                    })
                }

                webSocketManager.sendMessage(message.toString())
                messageInput.text.clear()
            } else {
                Toast.makeText(requireContext(), "El mensaje no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketManager.close()
    }

    private fun loadChatHistory(user1: String, user1Model: String, user2: String, user2Model: String) {
        RetrofitClient.authService.getMessages(user1, user1Model, user2, user2Model)
            .enqueue(object : Callback<MessagesResponse> {
                override fun onResponse(call: Call<MessagesResponse>, response: Response<MessagesResponse>) {
                    if (response.isSuccessful) {
                        val messages = response.body()?.messages ?: emptyList()


                        chatAdapter.clearMessages()
                        for (message in messages) {
                            val jsonMessage = JSONObject().apply {
                                put("sender", message.sender)
                                put("content", message.content)
                                put("timestamp", message.timestamp)
                            }
                            chatAdapter.addMessage(jsonMessage)
                        }
                    } else {
                        Log.e("ChatFragment", "Error al cargar mensajes: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MessagesResponse>, t: Throwable) {
                    Log.e("ChatFragment", "Error en la solicitud de mensajes: ${t.message}")
                }
            })
    }






    companion object {
        @JvmStatic
        fun newInstance(storeId: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString("tienda_id", storeId)
                }
            }
    }
}
