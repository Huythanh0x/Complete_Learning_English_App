package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.ActivityMainLoginBinding

class MainLoginActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    lateinit var binding: ActivityMainLoginBinding
    lateinit var navControler: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_login)
        supportActionBar?.hide()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navControler = navHostFragment.navController

//        navController = findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navControler)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navControler.navigateUp()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)

    }
}