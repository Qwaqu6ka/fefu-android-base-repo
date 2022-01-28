package ru.fefu.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import ru.fefu.activitytracker.databinding.ActivityDetailsBinding

class ActivityDetails : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        val username: TextView = binding.username
        val distance: TextView = binding.distance
        val time: TextView = binding.time
        val duration: TextView = binding.duration
        val startTime: TextView = binding.start
        val finishTime: TextView = binding.finish

        var activityExtras = intent.getStringExtra("Activity")
        activityExtras = activityExtras?.substringAfter('(')?.substringBefore(')')
        val fields = activityExtras?.split(", ")

        if (fields != null) {
            for (field in fields) {
                val temp = field.split("=")
                when (temp[0]) {
                    "id" -> distance.text = temp[1]
                    "activeType" -> toolbar.title = temp[1]
//                    "startTime" -> startTime.text = temp[1]
//                    "finishTime" -> finishTime.text = temp[1]
                }
            }
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }

    }
}
