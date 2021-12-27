package com.batdaulaptrinh.completlearningenglishapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.databinding.ActivityMainBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.notification.NotifyLearningWordWorker
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.ui.login.MainLoginActivity
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.time.Duration
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.DarkTheme)
//        } else {
//            setTheme(R.style.AppTheme)
//        }
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, MainLoginActivity::class.java))
            finish()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment_container)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_learning,
                R.id.navigation_chat,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
        supportActionBar?.hide()
        val notificationWord =
            intent.extras?.getParcelable<Word>(Utils.ID_NOTIFY_LEARNING_WORD_WORKER)
        if (notificationWord != null) {
            Timber.d("GET WORD FROM NOTIFICATION")
            findNavController(R.id.nav_host_fragment_container).navigate(
                R.id.action_navigation_home_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to notificationWord)
            )
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home || destination.id == R.id.navigation_learning || destination.id == R.id.navigation_chat || destination.id == R.id.navigation_profile) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
        updateNotificationLearningWord()
        Timber.plant(Timber.DebugTree())
    }

    private fun updateNotificationLearningWord() {
        val minuteTimeCycle =
            SharePreferencesProvider(applicationContext).getLoopNotification().toLong()
        val notificationWork =
            PeriodicWorkRequestBuilder<NotifyLearningWordWorker>(minuteTimeCycle, TimeUnit.MINUTES)
                .setInitialDelay(Duration.ofMinutes(minuteTimeCycle))
                .build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                Utils.UNIQUE_NOTIFY_LEARNING_WORD_WORKER, ExistingPeriodicWorkPolicy.REPLACE,
                notificationWork
            )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        Timber.d("\n" + navHostFragment.childFragmentManager.backStackEntryCount)
        if (binding.bottomNavigationView.selectedItemId == R.id.navigation_home && navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true

            MotionToast.createColorToast(
                this,
                "WARNING",
                "Please click BACK again to exit",
                MotionToastStyle.WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, R.font.helvetica_regular)
            )
            Handler(Looper.getMainLooper()).postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000)
        } else {
            super.onBackPressed()
        }
    }
}