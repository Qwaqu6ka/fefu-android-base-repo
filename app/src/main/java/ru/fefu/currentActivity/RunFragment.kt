package ru.fefu.currentActivity

import ru.fefu.activitytracker.R
import ru.fefu.basefragment.BaseFragment
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.fefu.activitytracker.databinding.FragmentRunBinding

class RunFragment : BaseFragment<FragmentRunBinding> (R.layout.fragment_run) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activityText: TextView = binding.activeText

        parentFragmentManager.setFragmentResultListener("prepareFragment", viewLifecycleOwner) {
            key, bundle ->
            activityText.text = bundle.getString("bundleKey")
        }
    }
}