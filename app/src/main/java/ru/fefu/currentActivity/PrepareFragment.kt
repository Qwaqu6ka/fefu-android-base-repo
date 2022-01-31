package ru.fefu.currentActivity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentPrepareBinding
import ru.fefu.basefragment.BaseFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.osmdroid.util.GeoPoint
import ru.fefu.database.ActiveTypes
import ru.fefu.database.Activity
import ru.fefu.database.App
import ru.fefu.mainmenu.MainPartActivity
import java.util.*

class PrepareFragment : BaseFragment<FragmentPrepareBinding> (R.layout.fragment_prepare) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler: RecyclerView = binding.recycler
        val button: Button = binding.startButton
        val adapter = RecyclerAdapter(ActiveTypes.values(), requireContext())
        val activity = (requireActivity() as MapActivity)

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

        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                activity.permissionRequestLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
            else {
                val newActivity = Activity(
                    0,
                    ActiveTypes.values()[activeObject],
                    Date(),
                    null,
                    "defalut@user", // имя если активность наша
                    listOf()
                )
                App.INSTANCE.db.activeDao().insert(newActivity)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.containerMapActivity, RunFragment())
                    .commit()

                activity.startLocationService()
            }
        }
    }
}