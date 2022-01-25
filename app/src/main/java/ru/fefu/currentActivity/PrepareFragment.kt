package ru.fefu.currentActivity

import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentPrepareBinding
import ru.fefu.basefragment.BaseFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.database.ActiveTypes
import ru.fefu.database.Activity
import ru.fefu.database.App
import java.util.*

class PrepareFragment : BaseFragment<FragmentPrepareBinding> (R.layout.fragment_prepare) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler: RecyclerView = binding.recycler
        val button: Button = binding.startButton
        val adapter = RecyclerAdapter(ActiveTypes.values(), requireContext())

        var activeObject = 0

        adapter.setItemClickListener {
            activeObject = it
        }

        recycler.adapter = adapter
        recycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        val runFragment = parentFragmentManager.findFragmentByTag("RunFragment")
        val prepareFragment = parentFragmentManager.findFragmentByTag("PrepareFragment")

        button.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                "prepareFragment",
                bundleOf("bundleKey" to ActiveTypes.values()[activeObject].value)
            )
            if (prepareFragment != null)
                if (runFragment != null)
                    parentFragmentManager.beginTransaction()
                        .hide(prepareFragment)
                        .show(runFragment)
                        .addToBackStack("RunFragment")
                        .commit()
                else
                    parentFragmentManager.beginTransaction()
                        .hide(prepareFragment)
                        .add(R.id.container, RunFragment(), "RunFragment")
                        .addToBackStack("RunFragment")
                        .commit()

            App.INSTANCE.db.activeDao().insert(
                Activity(
                    0,
                    ActiveTypes.values()[activeObject],
                    Date(),
                    Date()
                )
            )
        }
    }
}