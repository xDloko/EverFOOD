package com.stormcode.everfood.firstMain.activitys

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.stormcode.everfood.firstMain.FirstAppActivity
import com.stormcode.everfood.firstMain.Store
import com.stormcode.everfood.firstMain.adapters.StoresAdapter
import com.stormcode.everfood.R

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StoresAdapter
    private val storesList = mutableListOf<Store>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.main)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val headerView = navigationView.getHeaderView(0)
        val usuarioTextView: TextView = headerView.findViewById(R.id.nav_header_textView)
        val imagePerfil: ImageButton = headerView.findViewById(R.id.nav_header_imagePerfil)
        val buttonLogOut: Button = headerView.findViewById(R.id.nav_header_logout)


        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val email = sharedPreferences.getString("email", null)

        if (email != null) {
            usuarioTextView.text = email
        }

        imagePerfil.setOnClickListener {
            if (isLoggedIn) {
                // Redirigir al perfil si está logueado
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            } else {
                // Redirigir al login si no está logueado
                Toast.makeText(this, "No has iniciado sesión", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "No hay ninguna sesión", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuración del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewMenus)
        adapter = StoresAdapter(storesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two -> Toast.makeText(this, "Carrito", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three -> Toast.makeText(this, "Contáctanos", Toast.LENGTH_SHORT).show()
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
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
