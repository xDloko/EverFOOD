package com.stormcode.everfood.FirstMain

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.stormcode.everfood.R

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.main)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val headerView = navigationView.getHeaderView(0)
        val usuarioTexview: TextView = headerView.findViewById(R.id.nav_header_textView)
        val imagePerfil: ImageButton = headerView.findViewById(R.id.nav_header_imagePerfil)
        val buttonLogOut: Button = headerView.findViewById(R.id.nav_header_logout)

        //sharedinforeferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val email = sharedPreferences.getString("email", null)

        if (email != null) {
            usuarioTexview.text = email
        }

        imagePerfil.setOnClickListener {
            if (isLoggedIn) {
                //Redirrecionar al perfil si esta logeado
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            } else {
                //Redirrecionar al login si no esta logeado
                Toast.makeText(this, "No has iniciado Sesion", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, FirstAppActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val editor = sharedPreferences.edit()
        buttonLogOut.setOnClickListener {
            if (isLoggedIn) {
                val intent = Intent(this, FirstAppActivity::class.java)
                editor.remove("isLoggedIn")
                editor.remove("username")
                editor.remove("email")
                editor.apply()
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "No hay Ninguna Sesion", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_one -> Toast.makeText(this, "perfil", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two -> Toast.makeText(this, "carrito", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three -> Toast.makeText(this, "que mas", Toast.LENGTH_SHORT).show()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}