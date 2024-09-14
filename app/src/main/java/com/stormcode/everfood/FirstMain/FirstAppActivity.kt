package com.stormcode.everfood.FirstMain

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.stormcode.everfood.R
import android.widget.EditText
import com.stormcode.everfood.FirstMain.UserRepository


class FirstAppActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_app)

        userRepository = UserRepository(this)

        val usernameEditText: EditText = findViewById(R.id.username_input)
        val passwordEditText: EditText = findViewById(R.id.password_input)
        val loginBtn: Button = findViewById(R.id.login_button)

        loginBtn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (userRepository.authenticateUser(username, password)) {

            } else {
                print("usuario o contraseña incorrecta")
            }


        }

        val navigateButton: ImageButton = findViewById(R.id.back_button)

        navigateButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
