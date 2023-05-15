package com.famstudio.tiktok.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.famstudio.tiktok.R
import com.famstudio.tiktok.databinding.ActivityMainBinding
import com.famstudio.tiktok.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var drawerLayout: DrawerLayout = binding.drawerLayout
        binding.imageView4.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }
        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
            var intent  = Intent(this, LandingPageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        val nav_view : NavigationView = binding.navView
        val premimumbtn: ConstraintLayout = nav_view.getHeaderView(0).findViewById(R.id.nav_premium)
        val title: TextView = nav_view.getHeaderView(0).findViewById(R.id.textView13)
        val email: TextView = nav_view.getHeaderView(0).findViewById(R.id.textView15)
        val imageView: ImageView = nav_view.getHeaderView(0).findViewById(R.id.imageView7)
        premimumbtn.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, PremiumPackageActivity::class.java))
        }

        var sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        var name_ = sh.getString(PERSON_GIVEN_NAME, "").toString()
        var email_ = sh.getString(PERSON_EMAIL, "").toString()
        var url = sh.getString(PHOTO_URL, "").toString()

        title.text= name_
        email.text= email_
        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(imageView);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }


}