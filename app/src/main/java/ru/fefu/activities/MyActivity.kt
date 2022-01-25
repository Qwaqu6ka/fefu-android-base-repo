package ru.fefu.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentMyActivityBinding
import ru.fefu.basefragment.BaseFragment
import ru.fefu.database.App

class MyActivity : BaseFragment<FragmentMyActivityBinding>(R.layout.fragment_my_activity) {

    private val myAdapter = RecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter.setItemClickListener {
            val i = Intent(activity, ActivityDetails::class.java)
            i.putExtra("Activity", it.toString())
            startActivity(i)
        }

        App.INSTANCE.db.activeDao().getAll().observe(viewLifecycleOwner) {
            myAdapter.submitList(it)
        }
        binding.recycler.adapter = myAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
    }
}