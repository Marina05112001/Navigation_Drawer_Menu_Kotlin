package com.example.navigation_drawer_menu

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation_drawer_menu.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        //подключение view binding
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        setSupportActionBar(bindingClass.toolbar)

        //navView - это Navigation View в activity_main
        bindingClass.navView.setNavigationItemSelectedListener(this)
        bindingClass.navView.setItemIconTintList(null);

        //drawLay - это Drawer layout в activity_main
        //open_drawer и close_drawer прописываются в strings.xml

        val toggle = ActionBarDrawerToggle(
            this, bindingClass.drawLay, bindingClass.toolbar, R.string.open_drawer, R.string.close_drawer
        )
        //изменение цвета гамбургера
        toggle.drawerArrowDrawable?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)

        //изменение цвета пунктов самого меню Navigation View
        //bindingClass.navView.itemTextColor = ColorStateList.valueOf(Color.WHITE)

        bindingClass.drawLay.addDrawerListener(toggle)
        toggle.syncState()

        //fragment_container - это FrameLayout в activity_main

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            bindingClass.navView.setCheckedItem(R.id.menu_home)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.menu_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()
            R.id.menu_share -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShareFragment()).commit()
            R.id.menu_about -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AboutFragment()).commit()
            R.id.menu_logout ->  Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
        }
        bindingClass.drawLay.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (bindingClass.drawLay.isDrawerOpen(GravityCompat.START)) {
            bindingClass.drawLay.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.nav_top_right, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_top_about -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AboutFragment.newInstance("", ""))
                    .commit()
            }
            R.id.nav_top_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SettingsFragment.newInstance("", ""))
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

