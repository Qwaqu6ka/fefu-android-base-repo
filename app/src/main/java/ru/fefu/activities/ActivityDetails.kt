package ru.fefu.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.marginTop
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityDetailsBinding

class ActivityDetails : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        val username: TextView = binding.username
        val distance: TextView = binding.distance
        val time: TextView = binding.time
        val duration: TextView = binding.duration

        val arr = intent.getStringArrayExtra("itemInfo")
        distance.text = arr?.get(0)
        duration.text = arr?.get(1)
        toolbar.title = arr?.get(2)
        time.text = arr?.get(3)
        if (arr?.get(4) == null || arr[4] == "") {
            binding.root.removeView(username)
        }
        else
            username.text = arr[4]

        toolbar.setNavigationOnClickListener { onBackPressed() }

    }
}
