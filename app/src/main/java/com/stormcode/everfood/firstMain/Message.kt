package com.stormcode.everfood.firstMain

data class MessagesResponse(
    val messages: List<Message>
)
data class Message(
    val sender: String,
    val recipientId: String,
    val content: String,
    val timestamp: String
)
