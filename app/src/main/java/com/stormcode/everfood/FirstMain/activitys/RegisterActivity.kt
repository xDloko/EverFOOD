package com.stormcode.everfood.FirstMain.activitys

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stormcode.everfood.FirstMain.FirstAppActivity
import com.stormcode.everfood.FirstMain.api.RegisterRequest
import com.stormcode.everfood.FirstMain.api.RegisterResponse
import com.stormcode.everfood.FirstMain.api.RetrofitClient
import com.stormcode.everfood.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val correoEditText: EditText = findViewById(R.id.correo_input)
        val usernameEditText: EditText = findViewById(R.id.user_input)
        val passwordEditText: EditText = findViewById(R.id.password_input)
        val registerBtn: Button = findViewById(R.id.register_button)

        registerBtn.setOnClickListener {
            val email = correoEditText.text.toString()
            val password = passwordEditText.text.toString()
            val username = usernameEditText.text.toString()
            val loadingDialog = Dialog(this)
            loadingDialog.setContentView(R.layout.dialog)
            loadingDialog.setCancelable(false)
            loadingDialog.show()
            val request = RegisterRequest(email, password, username)

            Handler(Looper.getMainLooper()).postDelayed({
                RetrofitClient.authService.register(request)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (response.isSuccessful) {
                                val registerResponse = response.body()
                                val intent = Intent(this@RegisterActivity, FirstAppActivity::class.java)
                                loadingDialog.dismiss()
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Usuario Creado con exito ${registerResponse?.email}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                loadingDialog.dismiss()
                                val errorBody = response.errorBody()?.string()
                                val statusCode = response.code()
                                println("Error en el registro: $errorBody")
                                println("Código de estado: $statusCode")
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Error en el registro",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            loadingDialog.dismiss()
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error de conexión",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }, 3000)
        }







        val navigateButton: ImageButton = findViewById(R.id.back_button)

        navigateButton.setOnClickListener {
            val intent = Intent(this, FirstAppActivity::class.java)
            startActivity(intent)
            finish()
        }





    }
}
