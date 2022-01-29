package ru.fefu.currentActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.osmdroid.util.GeoPoint
import ru.fefu.activitytracker.LocationService
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentRunBinding
import ru.fefu.basefragment.BaseFragment
import ru.fefu.database.App

class RunFragment : BaseFragment<FragmentRunBinding> (R.layout.fragment_run) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activityText: TextView = binding.activeText

        val activity = (requireActivity() as MapActivity)

        parentFragmentManager.setFragmentResultListener("prepareFragment", viewLifecycleOwner) {
            key, bundle ->
            activityText.text = bundle.getString("bundleKey")
        }

//        val active = App.INSTANCE.db.activeDao().getLastActive()
//        if (active.finishTime == null) {
//            for (point in active.coordinates) {
//                val p = GeoPoint(
//                    point.latitude,
//                    point.longitude
//                )
//                activity.polyline.addPoint(p)
//            }
//        }

        binding.finishButton.setOnClickListener {
            val intent = Intent(activity, LocationService::class.java).apply {
                action = LocationService.ACTION_CANCEL
            }
            activity.startService(intent)
            activity.onBackPressed()
        }
    }
}