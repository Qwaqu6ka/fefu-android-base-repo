package ru.fefu.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import ru.fefu.activitytracker.CoordToDistance
import ru.fefu.activitytracker.databinding.ActivityDetailsBinding
import ru.fefu.database.Activity
import ru.fefu.mainmenu.MainPartActivity
import java.text.SimpleDateFormat
import java.util.*

class ActivityDetails : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        val json = intent.getStringExtra("Activity")
        val activity = Gson().fromJson(json, Activity::class.java)

        binding.toolbar.title = activity.activeType.value
        binding.start.text = SimpleDateFormat("H:m").format(activity.startTime)
        binding.finish.text = SimpleDateFormat("H:m").format(activity.finishTime!!)

        val distance = CoordToDistance.getDistanceFromLatLonInM(activity.coordinates)
        if (distance > 1000)
            binding.distance.text = "${"%.2f".format(distance / 1000)} км"
        else
            binding.distance.text = "${distance.toInt()} м"

        var millis = activity.finishTime.time - activity.startTime.time
        var hours = (millis / (60 * 60 * 1000)).toInt()
        var minutes = (millis / (60 * 1000)).toInt()
        if (hours > 0)
            binding.duration.text = "$hours часов ${minutes - 60 * hours} минут"
        else if (minutes > 0)
            binding.duration.text = "$minutes минут"
        else
            binding.duration.text = "Меньше минуты"

        millis = Date().time - activity.finishTime.time
        hours = (millis / (60 * 60 * 1000)).toInt()
        minutes = (millis / (60 * 1000)).toInt()
        if (hours > 24)
            binding.time.text = SimpleDateFormat("d M y").format(activity.finishTime)
        else if (hours > 0)
            binding.time.text = "$hours часов ${minutes - 60 * hours} минут"
        else if (minutes > 0)
            binding.time.text = "$minutes минут"
        else
            binding.time.text = "Меньше минуты"

        if (activity.userName == "defalut@user") // имя если активноть наша
            binding.username.text = ""
        else
            binding.username.text = activity.userName
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainPartActivity::class.java))
        finishAffinity()
    }
}
