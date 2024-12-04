package com.stormcode.everfood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stormcode.everfood.firstMain.FirstAppActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException

class forgotPassword : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val sendRequestButton = findViewById<Button>(R.id.sendRequestButton)

        sendRequestButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isNotEmpty()) {
                sendPasswordResetRequest(email)
            } else {
                Toast.makeText(this, "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }

        val navigateButton: ImageButton = findViewById(R.id.back_button)
        navigateButton.setOnClickListener {
            val intent = Intent(this, FirstAppActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendPasswordResetRequest(email: String) {
        val url = "https://api-server-production-12d3.up.railway.app/api/user/forgot-password"
        val json = JSONObject()
        json.put("email", email)

        val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), json.toString())
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@forgotPassword, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@forgotPassword, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@forgotPassword, "Error: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
