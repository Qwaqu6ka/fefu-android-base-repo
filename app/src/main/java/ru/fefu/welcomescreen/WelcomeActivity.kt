package ru.fefu.welcomescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import ru.fefu.activitytracker.databinding.ActivityWelcomeBinding
import ru.fefu.mainmenu.MainPartActivity

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        if (sharedPreferences.getString("token", "") != "") {
            startActivity(Intent(this, MainPartActivity::class.java))
            finish()
        }

        binding.regButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.logButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}