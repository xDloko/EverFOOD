package com.stormcode.everfood.FirstMain

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.stormcode.everfood.FirstMain.UserRepository
import com.stormcode.everfood.R


class FirstAppActivity : AppCompatActivity() {

    private  lateinit var userRepository : UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_app)

        userRepository = UserRepository(this)

        val usernameEditText : EditText = findViewById(R.id.username)
        val passwordEditText : EditText = findViewById(R.id.password)
        val loginBtn : Button = findViewById(R.id.login_btn)

        loginBtn.setOnClickListener {
            val username =  usernameInput.text.toString()
            val password =  passwordInput.text.toString()

            if (userRepository.authenticateUser(username, password)) {

            } else { print("usuario o contraseña incorrecta")}


        }
        }

        val navigateButton: ImageButton = findViewById(R.id.back_button)

        navigateButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
