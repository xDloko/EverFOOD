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


class RegisterActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)




        userRepository = UserRepository(this)

        val correoEditText: EditText = findViewById(R.id.correo_input)
        val usernameEditText: EditText = findViewById(R.id.user_input)
        val passwordEditText: EditText = findViewById(R.id.password_input)
        val registerBtn: Button = findViewById(R.id.login_button)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()



        registerBtn.setOnClickListener {
            val correo = correoEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val loadingDialog = Dialog(this)
            loadingDialog.setContentView(R.layout.dialog)
            loadingDialog.setCancelable(false)
            loadingDialog.show()
            Handler(Looper.getMainLooper()).postDelayed({
                if (userRepository.authenticateUser(username, password))
                {
                    val intent = Intent(this, MainActivity::class.java)
                    loadingDialog.dismiss()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("username", username)
                    editor.apply()
                    startActivity(intent)
                    finish()
                } else {
                    loadingDialog.dismiss()
                    Toast.makeText(this, "usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            }, 2000)




        }

        val navigateButton: ImageButton = findViewById(R.id.back_button)

        navigateButton.setOnClickListener {
            val intent = Intent(this, FirstAppActivity::class.java)
            startActivity(intent)
            finish()
        }





    }
}
