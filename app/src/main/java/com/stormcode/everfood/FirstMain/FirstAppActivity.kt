package com.stormcode.everfood.FirstMain

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
import com.stormcode.everfood.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstAppActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_app)


        val emailEditText: EditText = findViewById(R.id.email_input)
        val passwordEditText: EditText = findViewById(R.id.password_input)
        val loginBtn: Button = findViewById(R.id.login_button)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()



        loginBtn.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val loadingDialog = Dialog(this)
            loadingDialog.setContentView(R.layout.dialog)
            loadingDialog.setCancelable(false)
            loadingDialog.show()
            val request = LoginRequest(email, password)

            Handler(Looper.getMainLooper()).postDelayed({
                RetrofitClient.authService.login(request).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            val intent = Intent(this@FirstAppActivity, MainActivity::class.java)
                            loadingDialog.dismiss()
                            editor.putBoolean("isLoggedIn", true)
                            editor.putString("username", loginResponse?.username)
                            editor.putString("email", loginResponse?.email)
                            editor.apply()
                            startActivity(intent)
                            finish()
                            Toast.makeText(this@FirstAppActivity, "Bienvenido ${loginResponse?.username}", Toast.LENGTH_SHORT).show()
                        } else {
                            loadingDialog.dismiss()
                            Toast.makeText(this@FirstAppActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        loadingDialog.dismiss()
                        Toast.makeText(this@FirstAppActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
            }, 3000)


        }

        val navigateButton: ImageButton = findViewById(R.id.back_button)
        navigateButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val registerButton: Button = findViewById(R.id.Registrarse_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }





    }
}
