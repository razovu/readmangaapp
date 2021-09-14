package com.example.readmangaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navBar)
        val navController = findNavController(R.id.fragment_container)
        NavigationUI.setupWithNavController(bottomNavBar, navController)
    }

}