package ru.fefu.currentActivity

import android.content.Intent
import ru.fefu.activitytracker.R
import ru.fefu.basefragment.BaseFragment
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.fefu.activitytracker.LocationService
import ru.fefu.activitytracker.databinding.FragmentRunBinding

class RunFragment : BaseFragment<FragmentRunBinding> (R.layout.fragment_run) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activityText: TextView = binding.activeText

        parentFragmentManager.setFragmentResultListener("prepareFragment", viewLifecycleOwner) {
            key, bundle ->
            activityText.text = bundle.getString("bundleKey")
        }

        binding.finishButton.setOnClickListener {
            val intent = Intent(activity, LocationService::class.java).apply {
                action = LocationService.ACTION_CANCEL
            }
            activity?.startService(intent)
        }
    }
}