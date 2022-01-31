package ru.fefu.currentActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import org.osmdroid.util.GeoPoint
import ru.fefu.activitytracker.CoordToDistance
import ru.fefu.activitytracker.LocationService
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentRunBinding
import ru.fefu.basefragment.BaseFragment
import ru.fefu.database.App
import ru.fefu.database.Point

class RunFragment : BaseFragment<FragmentRunBinding>(R.layout.fragment_run) {


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timer.text = "00:01:46"

        val activity = (requireActivity() as MapActivity)

        val id = App.INSTANCE.db.activeDao().getLastActiveId()
        App.INSTANCE.db.activeDao().getActivityByIdLive(id).observe(viewLifecycleOwner) {
            if (it.coordinates.isNotEmpty()) {
                val p = GeoPoint(
                    it.coordinates.last().latitude,
                    it.coordinates.last().longitude
                )
                activity.polyline.addPoint(p)
                MapActivity.lastDrawnPoint =
                    Point(it.coordinates.last().latitude, it.coordinates.last().longitude)
            }

            val distance = CoordToDistance.getDistanceFromLatLonInM(it.coordinates)
            if (distance > 1000)
                binding.distance.text = "${"%.2f".format(distance / 1000)} км"
            else
                binding.distance.text = "${distance.toInt()} м"
        }


        val active = App.INSTANCE.db.activeDao().getLastActive()
        binding.activeText.text = active.activeType.value

        val distance = CoordToDistance.getDistanceFromLatLonInM(active.coordinates)
        if (distance > 1000)
            binding.distance.text = "${"%.2f".format(distance / 1000)} км"
        else
            binding.distance.text = "${distance.toInt()} м"

        for (point in active.coordinates) {
                val p = GeoPoint(
                    point.latitude,
                    point.longitude
                )
                activity.polyline.addPoint(p)
        }

        binding.finishButton.setOnClickListener {
            val intent = Intent(activity, LocationService::class.java).apply {
                action = LocationService.ACTION_CANCEL
            }
            activity.startService(intent)
            activity.onBackPressed()
        }
    }
}