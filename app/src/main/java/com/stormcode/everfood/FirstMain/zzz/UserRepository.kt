package com.stormcode.everfood.FirstMain.zzz

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

data class User(
    val username: String,
    val password: String
)

class UserRepository(private val context: Context) {

    private fun loadUsersFromAssets(): List<User> {
        val inputStream = context.assets.open("users.json")
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val userType = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(reader, userType)
    }

    fun authenticateUser(username: String, password: String): Boolean {
        val users = loadUsersFromAssets()
        return users.any { it.username == username && it.password == password }
    }
}