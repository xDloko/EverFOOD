package com.stormcode.everfood.firstMain.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.R
import org.json.JSONObject

class ChatAdapter(
    private val messages: MutableList<JSONObject>,
    private val currentUserId: String // ID del usuario actual
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }

    fun addMessage(message: JSONObject) {
        Log.d("ChatAdapter", "Mensaje recibido: ${message.getString("content")}")
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    fun clearMessages() {
        messages.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.getString("sender") == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layout = if (viewType == VIEW_TYPE_SENT) {
            R.layout.item_message_sent // Crea este archivo para mensajes enviados
        } else {
            R.layout.item_message_received // Crea este archivo para mensajes recibidos
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        val content = message.getString("content")
        Log.d("ChatAdapter", "Mensaje en la posición $position: $content")
        holder.messageText.text = message.getString("content")
    }

    override fun getItemCount() = messages.size

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.messageText)
    }
}

